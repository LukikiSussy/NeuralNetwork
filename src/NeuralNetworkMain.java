import CreateNetwork.*;

public class NeuralNetworkMain {

    public static NeuralNetwork network;

    public static void main(String[] args) {
        int[] layers = {2, 3, 2};
        network = new NeuralNetwork(layers);

        Graph.CreateGraph();
    }
}
