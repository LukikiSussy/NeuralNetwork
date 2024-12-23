package NeuralNetwork;

public class Layer {

    public final int LAYER_SIZE;
    public final int PREV_LAYER_SIZE;

    public double[] biases;
    public double[][] weights;

    public Layer(int LAYER_SIZE, int PREV_LAYER_SIZE) throws Error {
        if (LAYER_SIZE <= 0 || PREV_LAYER_SIZE <= 0)
            throw new Error("Invalid Layer sizes, minimum size needs to be at least 1");
        this.LAYER_SIZE = LAYER_SIZE;
        this.PREV_LAYER_SIZE = PREV_LAYER_SIZE; // for the first layer, the previous layer size is the input size

        this.biases = new double[LAYER_SIZE];
        this.weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];
    }

    public double[] Calculate(double[] inputs) throws Error {
        if (inputs.length != this.PREV_LAYER_SIZE)
            throw new Error(String.format("Invalid input size for layer. Is %d but should be %d", inputs.length,
                    this.LAYER_SIZE));

        double[] outputs = new double[LAYER_SIZE];

        for (int i = 0; i < LAYER_SIZE; i++) { // notes out
            double weighted_input = biases[i];
            for (int j = 0; j < PREV_LAYER_SIZE; j++) { // notes in
                weighted_input += inputs[j] * weights[i][j];
            }

            outputs[i] = ActivationFunction(weighted_input);
        }

        return outputs;
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

    private double ActivationFunction(double weighted_input) {
        return 1 / (1 + Math.exp(-weighted_input));
    }
}
