package CreateNetwork;

public class Layer {
    int numNodesIn, numNodesOut;
    double[][] weights;
    double[] biases;

    public Layer(int numNodesIn, int numNodesOut) {
        this.numNodesIn = numNodesIn;
        this.numNodesOut = numNodesOut;

        weights = new double[numNodesIn][numNodesOut];
        biases = new double[numNodesOut];
    }

    public void setBiases(double[] biasesIn) {

        for (int i = 0; i < numNodesOut; i++) {
            biases[i] = biasesIn[i];
        }

    }

    public void setWeights(double[][] weightsIn) {
        for (int i = 0; i < numNodesIn; i++) {
            for (int j = 0; j < numNodesOut; j++) {
                weights[i][j] = weightsIn[i][j];
            }
        }
    }

    public double[] calculateOutputs(double[] inputs) {

        double[] weightedInputs = new double[numNodesOut];

        for (int nodeOut = 0; nodeOut < numNodesOut; nodeOut++) {
            double weightedInput = biases[nodeOut];
            for (int nodeIn = 0; nodeIn < numNodesIn; nodeIn++) {
                weightedInput += inputs[nodeIn] * weights[nodeIn][nodeOut];
            }
            weightedInputs[nodeOut] = weightedInput;
        }

        return weightedInputs;
    }
}
