import NeuralNetwork.Network;
import NeuralNetwork.TrainSet;
import Visualization.DataVisualizer;
import Visualization.Frame;

import java.io.IOException;
import java.util.Arrays;

import DatasetLoaders.MnistLoader;
import DatasetLoaders.MnistMatrix;
import DatasetLoaders.SetMaker;

public class Main {

	public static void main(String[] args) throws IOException {

		Network NeuralNetwork = new Network("Test Network", 784, 1000, 250, 10);

		MnistMatrix[][] Mnist_matrices = SetMaker.PrepareMatrices(10000);
		TrainSet[] sets = SetMaker.MakeSets(Mnist_matrices, 784, 10);

		MnistMatrix[] full_matrix = Mnist_matrices[0];
		TrainSet train_set = sets[0];
		TrainSet test_set = sets[1];

		Frame frame = new Frame(500, full_matrix);

		NeuralNetwork.Train(train_set, test_set, 10000, 20, 0.5, 0.5);

		// new CreateGraph(NeuralNetwork, 1200, 1000);
	}
}
