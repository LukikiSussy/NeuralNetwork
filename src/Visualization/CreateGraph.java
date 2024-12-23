package Visualization;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

import NeuralNetwork.Network;

public class CreateGraph extends JFrame {

    public JLabel[][] Labels;

    public CreateGraph(Network NeuralNetwork, int Width, int Height) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setSize(Width, Height);
        this.setTitle("Neural Network Graph");
        this.setLayout(null);

        this.Labels = new JLabel[NeuralNetwork.NETWORK_SIZE][];

        for (int i = 0; i < NeuralNetwork.NETWORK_SIZE; i++) {
            this.Labels[i] = new JLabel[NeuralNetwork.NETWORK_LAYER_SIZES[i]];
            for (int j = 0; j < NeuralNetwork.NETWORK_LAYER_SIZES[i]; j++) {
                JLabel Label = new JLabel("Neuron"); // add bias of label
                this.Labels[i][j] = Label;

                Label.setOpaque(true);

                if (i != 0) {
                    String weights = Arrays.toString(NeuralNetwork.layers[i-1].weights[j]);
                    String str = "Bias: %f \n Weights: %s";

                    String label_text = String.format(str, NeuralNetwork.layers[i-1].biases[j], weights);

                    Label.setToolTipText(label_text);
                }
               
                // set the colors of the neurons
                if (i == 0)
                    Label.setBackground(Color.GREEN);
                else if (i == NeuralNetwork.NETWORK_SIZE - 1)
                    Label.setBackground(Color.CYAN);
                else
                    Label.setBackground(Color.LIGHT_GRAY);

                // bs so that the neurons are all on the screen and have a good size relative to
                // how many there are
                int max_length = Arrays.stream(NeuralNetwork.NETWORK_LAYER_SIZES).max().getAsInt();
                int max_lenght_or_width = Math.max(max_length, NeuralNetwork.NETWORK_SIZE);

                int neuron_size = Math.max((int) ((Width - 0.2 * Width) / max_lenght_or_width) - 40, 20);

                Label.setSize(neuron_size, neuron_size);
                Label.setLocation(
                        Width / (NeuralNetwork.NETWORK_SIZE + 1) * (i + 1) - neuron_size / 2,
                        Height / (NeuralNetwork.NETWORK_LAYER_SIZES[i] + 1) * (j + 1) - neuron_size / 2);

                this.add(Label);
            }
        }

        this.setVisible(true);
    }
}
