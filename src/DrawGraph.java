import javax.swing.JComponent;
import java.awt.*;

public class DrawGraph extends JComponent {
    public static void main(String[] args) {
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        double[] inputs;

        for (int x = 50; x < getWidth(); x++) {
            for (int y = 0; y < getHeight() - 50; y++) {

                inputs = new double[] { x * 10000, y * 10000 };
                if (NeuralNetworkMain.network.classify(inputs) == 0) {
                    g2.setColor(Color.red);
                } else {
                    g2.setColor(Color.blue);
                }
                g2.drawLine(x, y, x, y);
            }
        }
    }
}
