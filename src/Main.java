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

		//mnist_matrix = new MnistLoader().ReadData("MnistDataset/t10k-images.idx3-ubyte",
		//		"MnistDataset/t10k-labels.idx1-ubyte");

		//Frame frame = new Frame(500);
		TrainSet set = new TrainSet(784, 10);

		for (int i = 0; i < mnist_matrix.length; i++) {
			int random_x = (int) Math.floor(Math.random() * 10) - 5;
			int random_y = (int) Math.floor(Math.random() * 10) - 5;

			mnist_matrix[i].Translate(random_x, random_y);
			mnist_matrix[i].AddNoise(20);
			set.addData(mnist_matrix[i].GetInputs(), mnist_matrix[i].GetOutputs());
		}

		//NeuralNetwork.Train(set, 1000000, 10000, 0.5, 0.5);



		// new CreateGraph(NeuralNetwork, 1200, 1000);
	}
}
