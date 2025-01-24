package DatasetLoaders;

import java.io.IOException;

import NeuralNetwork.TrainSet;

public class SetMaker {
	public static MnistMatrix[][] PrepareMatrices(int reserve_for_test) throws IOException {
		MnistMatrix[] mnist_matrix = new MnistLoader().ReadData("MnistDataset/train-images.idx3-ubyte",
				"MnistDataset/train-labels.idx1-ubyte");

		if(reserve_for_test >= mnist_matrix.length) throw new IOException("Reserve bigger than set size");
		MnistMatrix[] input_matrix = new MnistMatrix[mnist_matrix.length - reserve_for_test];
		MnistMatrix[] test_matrix = new MnistMatrix[reserve_for_test];

		for (int i = 0; i < mnist_matrix.length; i++) {

			if (i < mnist_matrix.length - reserve_for_test) {
				input_matrix[i] = mnist_matrix[i].Clone();
			}
			else {
				test_matrix[i - input_matrix.length] = mnist_matrix[i].Clone();
			}
		}

		// mnist_matrix = new MnistLoader().ReadData("MnistDataset/t10k-images.idx3-ubyte",
		// "MnistDataset/t10k-labels.idx1-ubyte");

		return new MnistMatrix[][] {mnist_matrix, input_matrix, test_matrix};
	}

	public static TrainSet[] MakeSets (MnistMatrix[][] Mnist_matrices, int in, int out) {
		MnistMatrix[] input_matrix = Mnist_matrices[1];
		MnistMatrix[] test_matrix = Mnist_matrices[2];

		TrainSet train_set = new TrainSet(in, out);

		for (int i = 0; i < input_matrix.length; i++) {
			input_matrix[i].JumbleData();
			train_set.addData(input_matrix[i].GetInputs(), input_matrix[i].GetOutputs());
		}

		TrainSet test_set = new TrainSet(in, out);

		for (int i = 0; i < test_matrix.length; i++) {
			test_matrix[i].JumbleData();
			test_set.addData(test_matrix[i].GetInputs(), test_matrix[i].GetOutputs());
		}

		return new TrainSet[] {train_set, test_set};
	}
}
