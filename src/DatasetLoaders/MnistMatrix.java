package DatasetLoaders;

public class MnistMatrix {
	public int[][] data;
	public int label;

	public MnistMatrix(int n_rows, int n_cols, int label) {

		this.data = new int[n_rows][n_cols];
		this.label = label;
	}
}
