package NeuralNetwork;

import java.io.Serializable;

public class Layer implements Serializable {

	public final int LAYER_SIZE;
	public final int PREV_LAYER_SIZE;

	public double[] biases;
	public double[][] weights;

	public double[][] cost_gradient_weights;
	public double[] cost_gradient_biases;

	private double[][] prev_weight_increment;

	public double[] weighted_inputs;
	public double[] activations;
	public double[] inputs;

	public Layer(int LAYER_SIZE, int PREV_LAYER_SIZE) throws Error {
		if (LAYER_SIZE <= 0 || PREV_LAYER_SIZE <= 0)
			throw new Error("Invalid Layer sizes, minimum size needs to be at least 1");

		this.LAYER_SIZE = LAYER_SIZE; // nodes out
		this.PREV_LAYER_SIZE = PREV_LAYER_SIZE; // nodes in

		this.biases = new double[LAYER_SIZE];
		this.weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];

		// cost = how off the network is, this is the value the network is trying to minimize
		// the gradients represent the derivatives of the cost functions based on the weights and biases
		this.cost_gradient_weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];
		this.cost_gradient_biases = new double[LAYER_SIZE];

		// used for momentum
		this.prev_weight_increment = new double[LAYER_SIZE][PREV_LAYER_SIZE];

		this.weighted_inputs = new double[LAYER_SIZE]; // pre-activation function values
		this.activations = new double[LAYER_SIZE]; // outputs after going through the activation function
		this.inputs = new double[PREV_LAYER_SIZE];
	}

	public double[] Calculate(double[] inputs) throws Error {
		if (inputs.length != this.PREV_LAYER_SIZE)
			throw new Error(String.format("Invalid input size for layer. Is %d but should be %d", inputs.length,
					this.LAYER_SIZE));

		this.inputs = inputs; // used for backpropagation later

		for (int i = 0; i < LAYER_SIZE; i++) { // notes out
			this.weighted_inputs[i] = biases[i];

			for (int j = 0; j < PREV_LAYER_SIZE; j++) { // notes in
				this.weighted_inputs[i] += inputs[j] * weights[i][j];
			}

			this.activations[i] = ActivationFunction(weighted_inputs[i]);
		}

		return this.activations;
	}

	public void ApplyGradientsAndClear(double eta, double gamma) {

		for (int i = 0; i < LAYER_SIZE; i++) {
			biases[i] -= cost_gradient_biases[i] * eta;

			for (int j = 0; j < PREV_LAYER_SIZE; j++) {
				weights[i][j] -= cost_gradient_weights[i][j] * eta + this.prev_weight_increment[i][j] * gamma;
			}
		}

		// setting value for momentum calculation in next iteration
		this.prev_weight_increment = this.cost_gradient_weights;

		// clearing the cost gradient arrays
		this.cost_gradient_weights = new double[LAYER_SIZE][PREV_LAYER_SIZE];
		this.cost_gradient_biases = new double[LAYER_SIZE];
	}

	public void UpdateGradients(double[] node_values) {
		if (node_values.length != LAYER_SIZE)
			return;

		for (int i = 0; i < LAYER_SIZE; i++) {
			for (int j = 0; j < PREV_LAYER_SIZE; j++) {

				this.cost_gradient_weights[i][j] += node_values[i] * inputs[j];
			}

			this.cost_gradient_biases[i] += node_values[i];
		}
	}

	public double ActivationFunction(double weighted_input) {
		return 1 / (1 + Math.exp(-weighted_input));
	}

	public double ActivationDerivative(double weighted_input) {
		double activation = this.ActivationFunction(weighted_input);
		return activation * (1 - activation);
	}

	// MSE = Math.pow(output - desired_output, 2)
	public double MSEDerivative(double output, double desired_output) {
		return 2 * (output - desired_output);
	}

	// used only for the last layer (first in backpropagation)

	// node values are the derivative of the cost function times the derivative of the activation
	// function and are saved as they stay constant for each node
	public double[] CalculateOutputNodeValues(double[] expected_output) {
		double[] node_values = new double[expected_output.length];

		for (int i = 0; i < LAYER_SIZE; i++) {
			double d_cost = this.MSEDerivative(this.activations[i], expected_output[i]);
			double d_activation = this.ActivationDerivative(this.weighted_inputs[i]);
			node_values[i] = d_cost * d_activation;
		}

		return node_values;
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

	// these functions are used to set weights and biases, they are not needed in the backpropagation or
	// for normal functioning of the network but might be nice to have for testing purposes
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
