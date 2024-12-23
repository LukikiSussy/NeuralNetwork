import java.util.Arrays;

import NeuralNetwork.Network;
import Visualization.CreateGraph;

public class Main {
    public static void main(String[] args) throws Exception {
        Network NeuralNetwork = new Network(2, 3, 8, 2);

        //NeuralNetwork.SetBiases(new double[][] {{1, 2, 3}, {1, 2}});
        //NeuralNetwork.SetWeights(new double[][][] {{{1, 2}, {3, 4}, {1, 3}}, {{2, 4, 3}, {1, 4, 2}}});

        for (int i = 0; i < NeuralNetwork.layers.length; i++) {
            System.out.println(i);
                for(int j = 0; j < NeuralNetwork.layers[i].weights.length; j++) {
                    System.out.println(Arrays.toString(NeuralNetwork.layers[i].weights[j]));
                }
                System.out.println(Arrays.toString(NeuralNetwork.layers[i].biases));
        }

        double[] outputs = NeuralNetwork.Calculate(new double[] {1, 2});

        CreateGraph Visualization = new CreateGraph(NeuralNetwork, 1200, 1000);
    }
}
