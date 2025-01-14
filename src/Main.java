import NeuralNetwork.Network;
import NeuralNetwork.TrainSet;
import Visualization.DataVisualizer;
import Visualization.Frame;

import java.io.IOException;
import java.util.Arrays;

import DatasetLoaders.MnistLoader;
import DatasetLoaders.MnistMatrix;

public class Main {

	private static DataVisualizer v = new DataVisualizer(250);

	public static void main(String[] args) throws IOException {
		Network NeuralNetwork = new Network("Test Network", 4, 8, 4, 2);

		MnistMatrix[] mnist_matrix = new MnistLoader().ReadData("MnistDataset/train-images.idx3-ubyte",
				"MnistDataset/train-labels.idx1-ubyte");

		MnistMatrix[] input_matrix = new MnistMatrix[mnist_matrix.length];
		for (int i = 0; i < mnist_matrix.length; i++) {
			input_matrix[i] = mnist_matrix[i].Clone();
		}

		// mnist_matrix = new MnistLoader().ReadData("MnistDataset/t10k-images.idx3-ubyte",
		// "MnistDataset/t10k-labels.idx1-ubyte");

		Frame frame = new Frame(500, mnist_matrix);

		TrainSet set = new TrainSet(784, 10);

		for (int i = 0; i < mnist_matrix.length; i++) {
			input_matrix[i].JumbleData();
			set.addData(input_matrix[i].GetInputs(), input_matrix[i].GetOutputs());

		}

		// new CreateGraph(NeuralNetwork, 1200, 1000);
	}
}
