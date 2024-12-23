package NetworkTools;

public class RandomArray {
    public static double[] GenerateRandomArray(int length, double lower, double upper) throws Error {
        if (length <= 0 || upper-lower < 0) throw new Error("Invalid parameters");

        double[] outputs = new double[length];
        for (int i = 0; i < length; i++) {
            outputs[i] = Math.random() * (upper - lower) + lower;
        }

        return outputs;
    }
}