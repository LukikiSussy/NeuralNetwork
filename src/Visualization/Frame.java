package Visualization;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;

import DatasetLoaders.MnistMatrix;

public class Frame extends JFrame {

	private DataVisualizer visualizer;
	private JFrame window;

	private MnistMatrix[] data;
	private int display_index = 0;

	public Frame(int size, MnistMatrix[] data) {
		this.data = data;

		this.window = new JFrame();
		this.window.setResizable(false);
		this.window.setSize(size, size + 100);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.visualizer = new DataVisualizer(size);
		this.window.add(visualizer, BorderLayout.CENTER);

		// Create and customize the button panel
		JPanel button_panel = new JPanel();
		button_panel.setBackground(Color.DARK_GRAY);
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		// Create modern buttons
		JButton next_button = createModernButton("Reset");
		JButton randomButton = createModernButton("Random");

		// Add hover effects
		addHoverEffect(next_button, Color.DARK_GRAY, Color.GRAY);
		addHoverEffect(randomButton, Color.DARK_GRAY, Color.GRAY);

		// Add buttons to the panel
		button_panel.add(next_button);
		button_panel.add(randomButton);

		next_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				display_index++;
				if (display_index >= data.length) {
					display_index = 0;
				}
				DrawNumber(display_index);
			}
		});

		// Add the button panel to the frame
		window.add(button_panel, BorderLayout.SOUTH);

		window.setVisible(true);

		if (this.data.length != 0)
			this.DrawNumber(display_index);
	}

	public void DrawNumber(int index) {
		if (this.visualizer != null) {
			this.window.setTitle(String.valueOf(this.data[index].label));
			this.visualizer.SetData(this.data[index]);
			this.window.repaint();
		}
	}

	// Visuals
	private JButton createModernButton(String text) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Draw rounded background
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

				// Draw button text
				super.paintComponent(g2);
				g2.dispose();
			}

			@Override
			public void setContentAreaFilled(boolean b) {
				// Prevent the default filled behavior
			}
		};

		button.setPreferredSize(new Dimension(120, 40));
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("SansSerif", Font.BOLD, 14));
		button.setBorder(new RoundedBorder(20)); // Rounded corners
		button.setFocusPainted(false);
		button.setOpaque(false); // Ensures transparency outside the rounded area
		return button;
	}

	private void addHoverEffect(JButton button, Color defaultColor, Color hoverColor) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(defaultColor);
			}
		});
	}

	// Custom border for rounded corners
	static class RoundedBorder extends AbstractBorder {
		private final int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(c.getForeground());
			g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			g2.dispose();
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(radius, radius, radius, radius);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.left = insets.right = insets.top = insets.bottom = radius;
			return insets;
		}
	}
}