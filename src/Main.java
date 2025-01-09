import NeuralNetwork.Network;
import Visualization.DataVisualizer;
import Visualization.Frame;

import java.io.IOException;

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

		Frame frame = new Frame(500);
		frame.DrawNumber(mnist_matrix[10]);

		// new CreateGraph(NeuralNetwork, 1200, 1000);
	}
}
