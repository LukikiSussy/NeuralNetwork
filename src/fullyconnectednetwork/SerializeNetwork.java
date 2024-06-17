package fullyconnectednetwork;

import java.io.*;

public class SerializeNetwork {
    public static void serialize(Network net, String filename) {
        if (net == null || filename.length() <= 0)
            return;

        // adding file extension to file name
        if (filename.length() >= 4) {
            String extension = filename.substring(filename.length() - 4);
            if (!extension.equals(".ser"))
                filename = filename + ".ser";
        }

        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(net);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static Network deserialize(String filename) {
        // checking for correct file extension
        String extension = filename.substring(filename.length() - 4);
        if (!extension.equals(".ser"))
            filename = filename + ".ser";

        Network net = null;

        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            net = (Network) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized");
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

        return net;
    }

    public static void main(String[] args) {
        Network net = new Network(2, 2, 2);
        serialize(net, "restart");
        net = deserialize("restart");
        System.out.println(net);
    }
}
