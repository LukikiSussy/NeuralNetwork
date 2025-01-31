import NeuralNetwork.Network;
import NeuralNetwork.SerializeNetwork;
import NeuralNetwork.TrainSet;
import Visualization.Frame;

import java.io.IOException;
import java.util.Arrays;

import DatasetLoaders.MnistMatrix;
import DatasetLoaders.SetMaker;

public class Main {

	public static void main(String[] args) throws IOException {

		Network NeuralNetwork = new Network("Test Network", 4, 8, 4, 2);

		TrainSet set = new TrainSet(4, 2);
		set.addData(new double[] {0.1, 0.2, 0.3, 0.4}, new double[] {0.9, 0.1});
		set.addData(new double[] {0.9, 0.8, 0.7, 0.6}, new double[] {0.1, 0.9});
		set.addData(new double[] {0.3, 0.8, 0.1, 0.4}, new double[] {0.3, 0.7});
		set.addData(new double[] {0.9, 0.8, 0.1, 0.2}, new double[] {0.7, 0.3});
		set.addData(new double[] {0.2, 0.2, 0.3, 0.4}, new double[] {0.9, 0.1});
		set.addData(new double[] {0.9, 0.2, 0.7, 0.6}, new double[] {0.1, 0.9});
		set.addData(new double[] {0.3, 0.8, 0.2, 0.4}, new double[] {0.3, 0.7});
		set.addData(new double[] {0.9, 0.8, 0.1, 0.5}, new double[] {0.7, 0.3});

		NeuralNetwork.Train(set, set, 10000, 4, 0.3, 0);

		System.out.println(Arrays.toString(NeuralNetwork.Calculate(set.getInput(7))));

/*
		REWRITE TIME!!

		- cleaner code in main network file
		- way to easily implement multiple activation functions and switch between them
		- batch serialization with trainable parameters
		- remove bug where large networks will only output 1s
		- no fucking acceleration parameter, that shit is useless


		Network NeuralNetwork = new Network("Mnist_1", 784, 500, 10);

		MnistMatrix[][] Mnist_matrices = SetMaker.PrepareMatrices(10000);
		TrainSet[] sets = SetMaker.MakeSets(Mnist_matrices, 784, 10);

		MnistMatrix[] full_matrix = Mnist_matrices[0];
		TrainSet train_set = sets[0];
		TrainSet test_set = sets[1];
		
		Frame frame = new Frame(500, full_matrix);
		
		//NeuralNetwork.Train(train_set, test_set, 10000, 5000, 0.5, 0.5);

		double[] inputs = test_set.getInput(1);
		double[] desired_outputs = test_set.getOutput(1);
		double[] outputs = NeuralNetwork.Calculate(inputs);
		System.out.println(Arrays.toString(outputs));
		System.out.println(Arrays.toString(desired_outputs));

		// new CreateGraph(NeuralNetwork, 1200, 1000);

*/
	}
}
