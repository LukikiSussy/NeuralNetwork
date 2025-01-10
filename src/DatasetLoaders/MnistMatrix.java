package DatasetLoaders;

public class MnistMatrix {
	public int[][] data;
	public int label;

	public MnistMatrix(int n_rows, int n_cols, int label) {

		this.data = new int[n_rows][n_cols];
		this.label = label;
	}

	public void AddNoise(int noise_count) {
		for (int i = 0; i < noise_count; i++) {
			int point_x = (int) Math.floor(Math.random() * this.data.length);
			int point_y = (int) Math.floor(Math.random() * this.data[0].length);
			int intensity = (int) Math.floor(Math.random() * 255);

			this.data[point_y][point_x] = Math.max(this.data[point_y][point_x], intensity);
		}
	}

	public void Translate(int t_x, int t_y) {

		int[][] translated_data = new int[this.data.length][this.data[0].length];

		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[i].length; j++) {

				if (i - t_y >= this.data.length || i - t_y < 0 || j - t_x >= this.data[i].length || j - t_x < 0) {
					translated_data[i][j] = 0;
				}
				else {
					translated_data[i][j] = this.data[i - t_y][j - t_x];
				}
			}
		}

		this.data = translated_data;
	}

	public double[] GetInputs() {
		double[] array_1d = new double[this.data.length * this.data[0].length];

		int n = 0;

		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				array_1d[n] = (int) this.data[i][j];

				n++;
			}
		}

		return array_1d;
	}

	public double[] GetOutputs() {
		double[] outputs = new double[10];
		outputs[this.label] = 1.0;

		return outputs;
	}
}
