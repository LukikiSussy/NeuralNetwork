import CreateNetwork.*;

public class NeuralNetworkMain {

    public static NeuralNetwork network;

    public static void main(String[] args) {
        int[] layers = { 2, 10, 2 };
        network = new NeuralNetwork(layers);

        for (Layer l : network.layers) {
            l.setRandom();
        }

        /// *
        double[] inputs = { 1, 0.2 };
        int out = network.classify(inputs);
        double[] inputs2 = { 100, 0.122 };
        int out2 = network.classify(inputs2);
        double[] inputs3 = { 1, 12.2 };
        int out3 = network.classify(inputs3);
        double[] inputs4 = { 42, 0.2 };
        int out4 = network.classify(inputs4);
        // */

        if (out != out2 || out2 != out3 || out3 != out4) {
            Graph.CreateGraph();
        }

        // Graph.CreateSliders();
    }
}
