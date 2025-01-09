package Visualization;

import javax.swing.JFrame;

import DatasetLoaders.MnistMatrix;

public class Frame extends JFrame {

	private DataVisualizer visualizer;
	private JFrame window;

	public Frame(int size) {
		this.window = new JFrame();
		this.window.setResizable(false);
		this.window.setSize(size, size);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.visualizer = new DataVisualizer(size);
		this.window.add(visualizer);

		this.window.setVisible(true);
	}

	public void DrawNumber(MnistMatrix matrix) {
		System.out.println(this.visualizer != null);
		if (this.visualizer != null) {
			this.window.setTitle(String.valueOf(matrix.label));
			this.visualizer.SetData(matrix);
			this.window.repaint();
		}
	}
}
