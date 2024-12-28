import java.util.Arrays;

import NeuralNetwork.Network;
import NeuralNetwork.SerializeNetwork;
import NeuralNetwork.TrainSet;

public class Main {
	public static void main(String[] args) throws Exception {
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

		NeuralNetwork.Train(set, 10000000, 4, 0.3);

		NeuralNetwork.ContinueTraining(set);

		System.out.println(Arrays.toString(NeuralNetwork.Calculate(set.getInput(7))));

		// new CreateGraph(NeuralNetwork, 1200, 1000);
	}
}
