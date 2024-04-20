import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Graph extends JFrame {

    static JLabel wLabel;

    static JSlider weight1_1;
    static JSlider weight2_1;
    static JSlider weight1_2;
    static JSlider weight2_2;

    static JLabel bLabel;
    static JSlider bias1;
    static JSlider bias2;

    public static void main(String[] args) {
    }

    public static void CreateGraph() {
        JFrame window = new JFrame();
        window.setResizable(false);
        window.setSize(1000, 1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Graph");

        DrawGraph drawGraph = new DrawGraph();
        window.add(drawGraph);

        window.setVisible(true);


        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.repaint();
            }
        });
        timer.start();
    }

    public static void CreateSliders() {
        JFrame f = new JFrame("frame");
        f.setSize(300, 1000);
        JPanel p = new JPanel();
 
        wLabel = new JLabel();
        wLabel.setText("Weights");
        weight1_1 = new JSlider(-100, 100, 0);
        weight2_1 = new JSlider(-100, 100, 0);
        weight1_2 = new JSlider(-100, 100, 0);
        weight2_2 = new JSlider(-100, 100, 0);

        bLabel = new JLabel();
        bLabel.setText("Biases");
        bias1 = new JSlider(-100, 100, 0);
        bias2 = new JSlider(-100, 100, 0);

        p.add(wLabel);
        p.add(weight1_1);
        p.add(weight2_1);
        p.add(weight1_2);
        p.add(weight2_2);

        p.add(bLabel);
        p.add(bias1);
        p.add(bias2);

        f.add(p);

        f.setVisible(true);
    }
}
