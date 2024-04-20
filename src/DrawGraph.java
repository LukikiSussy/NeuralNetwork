import javax.swing.JComponent;
import java.awt.*;

public class DrawGraph extends JComponent {
    public static void main(String[] args) {
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        NeuralNetworkMain.classify(100, 100);

        for (int x = 50; x < getWidth(); x++) {
            for (int y = 0; y < getHeight() - 50; y++) {

                if (NeuralNetworkMain.classify(x, y) == 0) {
                    g2.setColor(Color.red);
                } else {
                    g2.setColor(Color.blue);
                }
                g2.drawLine(x, y, x, y);
            }
        }
    }
}
