package NeuralNetwork;

import java.lang.reflect.Array;
import java.util.Arrays;

import NetworkTools.NetworkTools;

public class Network {
    public final int[] NETWORK_LAYER_SIZES;
    public final int NETWORK_SIZE;
    public final int INPUT_SIZE;
    public Layer[] layers;

    public Network(int... NETWORK_LAYER_SIZES) {
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.layers = new Layer[NETWORK_SIZE - 1]; // 1. layer is on index 0, 0th layer(input) is not in the list

        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];

        // creates the layers and set the weights and biases to random numbers from 0 to
        // 1
        for (int i = 0; i < NETWORK_SIZE - 1; i++) {
            this.layers[i] = new Layer(NETWORK_LAYER_SIZES[i + 1], NETWORK_LAYER_SIZES[i]);

            this.layers[i].AssignBiases(NetworkTools.GenerateRandomArray(NETWORK_LAYER_SIZES[i + 1], 0, 1));

            double[][] random_weights = new double[NETWORK_LAYER_SIZES[i + 1]][];

            for (int j = 0; j < NETWORK_LAYER_SIZES[i + 1]; j++) {
                random_weights[j] = NetworkTools.GenerateRandomArray(NETWORK_LAYER_SIZES[i], 0, 1);
            }

            this.layers[i].AssignWeights(random_weights);
        }
    }

    public double[] Calculate(double[] inputs) {

        for (int i = 0; i < this.layers.length; i++) {
            inputs = this.layers[i].Calculate(inputs);
        }

        return inputs;
    }

    public void SetWeights(double[][][] weights) throws Error {
        if (weights.length != NETWORK_SIZE - 1)
            throw new Error("Invalid weights array size");

        for (int i = 0; i < this.layers.length; i++) {
            this.layers[i].AssignWeights(weights[i]);
        }
    }

    public void SetBiases(double[][] biases) {
        if (biases.length != NETWORK_SIZE - 1)
            throw new Error("Invalid biases array size");

        for (int i = 0; i < this.layers.length; i++) {
            this.layers[i].AssignBiases(biases[i]);
        }
    }

    public void Train(TrainSet set, int loops, int batch_size, double eta) {
        for (int i = 0; i < loops; i++) {
            TrainSet batch = set.extractBatch(batch_size);

            for (int j = 0; j < batch_size; j++) {
                this.UpdateAllGradients(batch.getInput(j), batch.getOutput(j));
            }

            this.ApplyAllGradientsAndClear(eta);
        }
    }

    private void ApplyAllGradientsAndClear(double eta) {
        for (int i = 0; i < layers.length; i++) {
            layers[i].ApplyGradientsAndClear(eta);
        }
    }

    private void UpdateAllGradients(double[] input, double[] expected_output) {
        this.Calculate(input);

        Layer last_layer = this.layers[layers.length - 1];
        double[] node_values = last_layer.CalculateOutputNodeValues(expected_output);

        last_layer.UpdateGradients(node_values);


        for (int hidden_layer_index = layers.length - 2; hidden_layer_index >= 0; hidden_layer_index--) {

            Layer hidden_layer = this.layers[hidden_layer_index];
            node_values = hidden_layer.CalculateHiddenNodeValues(this.layers[hidden_layer_index + 1], node_values);
            hidden_layer.UpdateGradients(node_values);
        }
    }
}
