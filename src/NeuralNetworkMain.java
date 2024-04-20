public class NeuralNetworkMain {

    public static int classify(double input1, double input2) {
        double weight1_1 = (double) Graph.weight1_1.getValue()/100;
        double weight2_1 = (double) Graph.weight2_1.getValue()/100;
        double weight1_2 = (double) Graph.weight1_2.getValue()/100;
        double weight2_2 = (double) Graph.weight2_2.getValue()/100;

        double bias1 = (double) Graph.bias1.getValue()*10;
        double bias2 = (double) Graph.bias2.getValue()*10;

        double output1 = input1 * weight1_1 + input2 * weight2_1 + bias1;
        double output2 = input1 * weight1_2 + input2 * weight2_2 + bias2;

        if(output1 > output2) return 0;
        else return 1;
    }

    public static void main(String[] args) {
        Graph.CreateGraph();
        Graph.CreateSliders();
    }
}
