package Visualization;

import DatasetLoaders.MnistMatrix;

import javax.swing.JPanel;
import java.awt.*;

public class DataVisualizer extends JPanel {

	private int size;
	private int[][] data;
	private int label;

	private int pixel_size_x;
	private int pixel_size_y;

	public DataVisualizer(int size) {
		repaint();
		this.setSize(size, size);
		this.setLayout(null);

		this.size = size;
	}

	public void SetData(MnistMatrix matrix) {
		this.data = matrix.data;
		this.label = matrix.label;
		this.pixel_size_x = size / this.data.length;
		this.pixel_size_y = size / this.data[0].length;

		System.out.println("Data Set");
	}

	public void paintComponent(Graphics g) {

		if (this.data == null)
			return;

		Graphics2D g2 = (Graphics2D) g;
		System.out.println(this.label);

		for (int y = 0; y < this.data.length; y++) {
			for (int x = 0; x < this.data[0].length; x++) {

				int brightness = this.data[y][x];
				Color c = new Color(brightness, brightness, brightness);
				g2.setColor(c);
				
				g2.fillRect(x * pixel_size_x, y*pixel_size_y, pixel_size_x, pixel_size_y);
			}
		}
	}

}
