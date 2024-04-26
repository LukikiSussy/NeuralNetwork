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

    public void setRandom() {
        double[][] weightsIn = new double[numNodesIn][numNodesOut];
        double[] biasesIn = new double[numNodesOut];

        for (int i = 0; i < numNodesIn; i++) {
            for (int j = 0; j < numNodesOut; j++) {
                weightsIn[i][j] = (Math.random() - 0.5) * 4;
            }
        }

        for (int i = 0; i < numNodesOut; i++) {
            biasesIn[i] = (Math.random() - 0.5);
        }

        this.setWeights(weightsIn);
        this.setBiases(biasesIn);
    }

    public double[] calculateOutputs(double[] inputs) {

        double[] weightedInputs = new double[numNodesOut];

        for (int nodeOut = 0; nodeOut < numNodesOut; nodeOut++) {
            double weightedInput = 0;
            for (int nodeIn = 0; nodeIn < numNodesIn; nodeIn++) {
                weightedInput += inputs[nodeIn] * weights[nodeIn][nodeOut];
            }
            double activatedInput = Math.tanh(weightedInput) + biases[nodeOut];
            weightedInputs[nodeOut] = activatedInput;
        }

        return weightedInputs;
    }
}
