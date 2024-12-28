package NetworkTools;

import java.util.Random;

public class NetworkTools {
    public static double[] GenerateRandomArray(int length, double lower, double upper) throws Error {
        if (length <= 0 || upper-lower < 0) throw new Error("Invalid parameters");

        double[] outputs = new double[length];
        for (int i = 0; i < length; i++) {
            outputs[i] = Math.random() * (upper - lower) + lower;
        }

        return outputs;
    }

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

        array[r] = array[range-1];
        range--;
    }

    return result;
}
}