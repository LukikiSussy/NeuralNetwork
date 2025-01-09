package DatasetLoaders;

import java.io.*;

// credit to https://github.com/turkdogan/mnist-data-reader/blob/master/Main.java
public class MnistLoader {

	public MnistMatrix[] ReadData(String data_path, String label_path) throws IOException {

		DataInputStream data_input_stream = new DataInputStream(
				new BufferedInputStream(new FileInputStream(data_path)));
		int magic_number = data_input_stream.readInt();
		int number_of_items = data_input_stream.readInt();
		int n_rows = data_input_stream.readInt();
		int n_cols = data_input_stream.readInt();

		System.out.println("magic number is " + magic_number);
		System.out.println("number of items is " + number_of_items);
		System.out.println("number of rows is: " + n_rows);
		System.out.println("number of cols is: " + n_cols);

		DataInputStream label_input_stream = new DataInputStream(
				new BufferedInputStream(new FileInputStream(label_path)));
		int label_magic_number = label_input_stream.readInt();
		int number_of_labels = label_input_stream.readInt();

		System.out.println("labels magic number is: " + label_magic_number);
		System.out.println("number of labels is: " + number_of_labels);

		assert number_of_items == number_of_labels;

		MnistMatrix[] data = new MnistMatrix[number_of_items];

		for (int i = 0; i < number_of_items; i++) {
			int label = label_input_stream.readUnsignedByte();
			MnistMatrix mnistMatrix = new MnistMatrix(n_rows, n_cols, label);
			for (int r = 0; r < n_rows; r++) {
				for (int c = 0; c < n_cols; c++) {
					mnistMatrix.data[r][c] = data_input_stream.readUnsignedByte();
				}
			}
			data[i] = mnistMatrix;
		}

		data_input_stream.close();
		label_input_stream.close();

		return data;
	}
}