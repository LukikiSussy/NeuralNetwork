package NeuralNetwork;

import java.io.Serializable;

public class TrainingTracker implements Serializable {
	public int batch_size;
	public double eta;
	public double gamma;

	public int training_loops_finished;
	public int training_loops;

	public TrainingTracker(int batch_size, double eta, double gamma, int training_loops_finished, int training_loops) {
		this.batch_size = batch_size;
		this.eta = eta;
		this.gamma = gamma;

		this.training_loops_finished = training_loops_finished;
		this.training_loops = training_loops;
	}
}
