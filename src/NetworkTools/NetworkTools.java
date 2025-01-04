package NetworkTools;

import java.util.Random;

public class NetworkTools {
	public static double[] GenerateRandomArray(int length, double lower, double upper) throws Error {
		if (length <= 0 || upper - lower < 0)
			throw new Error("Invalid parameters");

		double[] outputs = new double[length];
		for (int i = 0; i < length; i++) {
			outputs[i] = Math.random() * (upper - lower) + lower;
		}

		return outputs;
	}

	public static double[][] GenerateRandomArray(int length_1, int length_2, double lower, double upper) throws Error {
		if (length_1 <= 0 || length_2 <= 0 || upper - lower < 0)
			throw new Error("Invalid parameters");

		double[][] outputs = new double[length_1][length_2];
		for (int i = 0; i < length_1; i++) {
			outputs[i] = GenerateRandomArray(length_2, lower, upper);
		}

		return outputs;
	}

	// return n random unique integers within the bounds
	public static int[] randomInts(int lower, int upper, int n) {
		int[] result = new int[n];

		int range = upper - lower + 1;
		int[] array = new int[range];

		for (int i = 0; i < range; i++) {
			array[i] = i + lower;
		}

		Random gen = new Random();

		for (int i = 0; i < n; i++) {
			int r = gen.nextInt(range);

			result[i] = array[r];

			array[r] = array[range - 1];
			range--;
		}

		return result;
	}
}