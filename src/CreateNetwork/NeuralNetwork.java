package CreateNetwork;
public class NeuralNetwork {
    Layer[] layers;

    public NeuralNetwork(int[] layerSizes) {
        layers = new Layer[layerSizes.length - 1];
        for (int i = 0; i < layerSizes.length - 1; i++) {
            layers[i] = new Layer(layerSizes[i], layerSizes[i] + 1);
        }
    }

    double[] calculateOutputs(double[] inputs) {

        for (Layer layer : layers) {
            inputs = layer.calculateOutputs(inputs);
        }
        return inputs;
    }

    public int classify(double[] inputs) {
        double[] outputs = calculateOutputs(inputs);

        int maxAt = 0;

        for (int i = 0; i < outputs.length; i++) {
            maxAt = outputs[i] > outputs[maxAt] ? i : maxAt;
        }

        return maxAt;
    }
}