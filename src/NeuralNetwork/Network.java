package NeuralNetwork;

import NetworkTools.NetworkTools;
import java.io.Serializable;
import java.util.Arrays;

// https://www.youtube.com/watch?v=hfMk-kjRv4c&t=2370s
public class Network implements Serializable {
	public final String NAME;

	public final int[] NETWORK_LAYER_SIZES;
	public final int NETWORK_SIZE;
	public final int INPUT_SIZE;
	public Layer[] layers;

	public double train_acc;
	private boolean was_correct;
	private int[] past_output_correctness;

	private int SERIALIZE_AFTER = 5;

	private TrainingTracker training_tracker;

	public Network(String name, int... NETWORK_LAYER_SIZES) {
		this.NAME = name;

		this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
		this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
		this.layers = new Layer[NETWORK_SIZE - 1]; // 1. layer is on index 0, 0th layer(input) is not in the list

		this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];

		this.past_output_correctness = new int[100];

		// creates the layers and set the weights and biases to random numbers from 0 to 1
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

	// runs the inputs through the network
	public double[] Calculate(double[] inputs) {
		for (int i = 0; i < this.layers.length; i++) {
			inputs = this.layers[i].Calculate(inputs);
		}

		return inputs;
	}

	public double[] EvaluateAccuracy(TrainSet set, TrainSet test_set) {
		int train_sum = 0;
		for (int i = 0; i < past_output_correctness.length; i++) {
			train_sum += past_output_correctness[i];
		}
		double train_correctness_percentage = train_sum * 100 / past_output_correctness.length;

		int test_sum = 0;
		TrainSet batch = test_set.extractBatch(past_output_correctness.length);
		for (int i = 0; i < batch.size(); i++) {
			double[] network_outputs = this.Calculate(batch.getInput(i));
			test_sum += IsCorrectlyClassified(network_outputs, batch.getOutput(i)) ? 1 : 0;
		}
		double test_correctness_percentage = test_sum * 100 / batch.size();

		return new double[] {train_correctness_percentage, test_correctness_percentage};
	}

	// batch size = how many values from the set will be used for each loop of the training cycle
	// (values are randomly selected)

	// eta = learning rate
	// gamma = momentum factor
	// if set too high, the weights and biases will have trouble settling
	// if set to high, the network will take longer to learn
	public void Train(TrainSet set, TrainSet test_set, int loops, int batch_size, double eta, double gamma) {
		loops = Math.max(loops, past_output_correctness.length);
		batch_size = Math.min(batch_size, set.size());

		this.training_tracker = new TrainingTracker(batch_size, eta, gamma, 0, loops);

		for (int i = 0; i < loops; i++) {
			TrainSet batch = set.extractBatch(batch_size);

			this.Train(batch, eta, gamma);

			this.past_output_correctness[i % past_output_correctness.length] = was_correct ? 1 : 0;

			this.UpdateLoadingBar(loops, i, set, test_set);
			this.training_tracker.training_loops_finished = i;

			if (i % SERIALIZE_AFTER == 0 && i != 0) {
				SerializeNetwork.serialize(this, this.NAME);
			}
		}

		System.out.println();
		SerializeNetwork.serialize(this, this.NAME);
	}

	// training from parameters preconfigured before serialization
	public void ContinueTraining(TrainSet set, TrainSet test_set) {
		for (int i = this.training_tracker.training_loops_finished; i < this.training_tracker.training_loops; i++) {
			TrainSet batch = set.extractBatch(this.training_tracker.batch_size);

			this.Train(batch, this.training_tracker.eta, this.training_tracker.gamma);
			this.UpdateLoadingBar(this.training_tracker.training_loops, i, set, test_set);
			this.training_tracker.training_loops_finished = i;

			if (i % SERIALIZE_AFTER == 0 && i != 0) {
				SerializeNetwork.serialize(this, this.NAME);
			}
		}

		System.out.println();
		SerializeNetwork.serialize(this, this.NAME);
	}

	private void Train(TrainSet batch, double eta, double gamma) {
		for (int j = 0; j < batch.size(); j++) {
			this.UpdateAllGradients(batch.getInput(j), batch.getOutput(j));
		}
		this.ApplyAllGradientsAndClear(eta, gamma);
	}

	private void UpdateLoadingBar(int total_loops, int loops, TrainSet train_set, TrainSet test_set) {
		int LOG_AFTER = total_loops / 100;
		if (loops % LOG_AFTER == 0) {
			int p_done = (loops * 100) / total_loops + 1;
			System.out.print("\r" + "-".repeat(p_done) + p_done + "% " + Arrays.toString(this.EvaluateAccuracy(train_set, test_set)));
		}
	}

	private void ApplyAllGradientsAndClear(double eta, double gamma) {
		for (int i = 0; i < layers.length; i++) {
			layers[i].ApplyGradientsAndClear(eta, gamma);
		}
	}

	// backpropagation
	private void UpdateAllGradients(double[] input, double[] expected_output) {
		// runs the input through the network so that each layer will have the necessary data saved to
		// precede with the backpropagation
		this.Calculate(input);

		// node values are calculated differently for the last layer
		Layer last_layer = this.layers[layers.length - 1];
		double[] node_values = last_layer.CalculateOutputNodeValues(expected_output);

		last_layer.UpdateGradients(node_values);

		// loops though the hidden layers and calculates the cost gradients
		for (int hidden_layer_index = layers.length - 2; hidden_layer_index >= 0; hidden_layer_index--) {

			Layer hidden_layer = this.layers[hidden_layer_index];
			node_values = hidden_layer.CalculateHiddenNodeValues(this.layers[hidden_layer_index + 1], node_values);
			hidden_layer.UpdateGradients(node_values);

			this.was_correct = IsCorrectlyClassified(last_layer.activations, expected_output);
		}
	}

	private boolean IsCorrectlyClassified(double[] output, double[] expected_output) {
		// check if the network was correct
		int net_classification = NetworkTools.highestValInArray(output);
		int expected_classification = NetworkTools.highestValInArray(expected_output);

		return net_classification == expected_classification;
	}
}
