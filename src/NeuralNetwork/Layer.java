package NeuralNetwork;

import java.util.Arrays;

public class Layer {

    public final int LAYER_SIZE;
    public final int PREV_LAYER_SIZE;

    public double[] biases;
    public double[][] weights;

    public double[][] cost_gradient_weights;
    public double[] cost_gradient_biases;

    public double[] weighted_inputs;
    public double[] activations;
    public double[] inputs;

    public Layer(int LAYER_SIZE, int PREV_LAYER_SIZE) throws Error {
        if (LAYER_SIZE <= 0 || PREV_LAYER_SIZE <= 0)
            throw new Error("Invalid Layer sizes, minimum size needs to be at least 1");
        this.LAYER_SIZE = LAYER_SIZE;
        this.PREV_LAYER_SIZE = PREV_LAYER_SIZE; // for the first layer, the previous layer size is the input size

        this.biases = new double[LAYER_SIZE];
        this.weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];

        this.cost_gradient_weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];
        this.cost_gradient_biases = new double[LAYER_SIZE];

        this.weighted_inputs = new double[LAYER_SIZE]; // pre-activation function values
        this.activations = new double[LAYER_SIZE]; // outputs after going through the activation function
        this.inputs = new double[PREV_LAYER_SIZE];
    }

    public double[] Calculate(double[] inputs) throws Error {
        if (inputs.length != this.PREV_LAYER_SIZE)
            throw new Error(String.format("Invalid input size for layer. Is %d but should be %d", inputs.length,
                    this.LAYER_SIZE));

        this.inputs = inputs;

        for (int i = 0; i < LAYER_SIZE; i++) { // notes out
            this.weighted_inputs[i] = biases[i];
            for (int j = 0; j < PREV_LAYER_SIZE; j++) { // notes in
                this.weighted_inputs[i] += inputs[j] * weights[i][j];
            }

            this.activations[i] = ActivationFunction(weighted_inputs[i]);
        }

        return this.activations;
    }


    public void ApplyGradientsAndClear(double eta) {
        
        for (int i = 0; i < LAYER_SIZE; i++) {
            biases[i] -= cost_gradient_biases[i] * eta;

            for (int j = 0; j < PREV_LAYER_SIZE; j++) {
                weights[i][j] -= cost_gradient_weights[i][j] * eta; 
            }
        }

        this.cost_gradient_weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];
        this.cost_gradient_biases = new double[LAYER_SIZE];
    }


    public double ActivationFunction(double weighted_input) {
        return 1 / (1 + Math.exp(-weighted_input));
    }

    public double ActivationDerivative(double weighted_input) {
        double activation = this.ActivationFunction(weighted_input);
        return activation * (1 - activation);
    }


    // calculate error from desired outputs
    public double MSE(double output, double desired_output) {
        return Math.pow(output - desired_output, 2);
    }

    public double MSEDerivative(double output, double desired_output) {
        return 2 * (output - desired_output);
    }



    public void UpdateGradients(double[] node_values) {
        if (node_values.length != LAYER_SIZE);

        for (int i = 0; i < LAYER_SIZE; i++) {
            for (int j = 0; j < PREV_LAYER_SIZE; j++) {

                this.cost_gradient_weights[i][j] += node_values[i] * inputs[j]; 
            }

            this.cost_gradient_biases[i] += node_values[i];
        }
    }


    public double[] CalculateHiddenNodeValues(Layer prev_layer, double[] prev_node_values) {
        double[] node_values = new double[LAYER_SIZE];

        for (int i = 0; i < LAYER_SIZE; i++) {

            double current_node_value = 0;

            for (int j = 0; j < prev_node_values.length; j++) {
                double d_weighted_input = prev_layer.weights[j][i];
                current_node_value += d_weighted_input * prev_node_values[j];
            }
            current_node_value *= ActivationDerivative(weighted_inputs[i]);
            node_values[i] = current_node_value;
        }
        
        return node_values;
    }

    public double[] CalculateOutputNodeValues(double[] expected_output) {
        double[] node_values = new double[expected_output.length];

        for (int i = 0; i < LAYER_SIZE; i++) {
            double d_cost = this.MSEDerivative(this.activations[i], expected_output[i]);
            double d_activation = this.ActivationDerivative(this.weighted_inputs[i]);
            node_values[i] = d_cost * d_activation;
        }

        return node_values;
    }



    public void AssignWeights(double[][] weights) throws Error {
        if (weights.length != LAYER_SIZE || weights[0].length != PREV_LAYER_SIZE)
            throw new Error("Invalid weights array size");
        this.weights = weights;
    }

    public void AssignBiases(double[] biases) throws Error {
        if (biases.length != LAYER_SIZE)
            throw new Error("Invalid biases array size");
        this.biases = biases;
    }
}
