package NeuralNetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		else {
			filename = filename + ".ser";
		}

		try {
			// Saving of object in a file
			Files.createDirectories(Paths.get("SerializedNetworks"));
			File targetDir = new File("SerializedNetworks");
			File targetFile = new File(targetDir, filename);

			FileOutputStream file = new FileOutputStream(targetFile);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(net);

			out.close();
			file.close();
		}

		catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public static Network deserialize(String filename) {
		// checking for correct file extension
		System.out.println(filename);
		if (filename.length() >= 4) {
			String extension = filename.substring(filename.length() - 4);
			if (!extension.equals(".ser"))
				filename = filename + ".ser";
		}
		else {
			filename = filename + ".ser";
		}

		Network net = null;

		try {
			// Reading the object from a file
			File targetDir = new File("SerializedNetworks");
			File targetFile = new File(targetDir, filename);
			FileInputStream file = new FileInputStream(targetFile);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			net = (Network) in.readObject();

			in.close();
			file.close();

			System.out.println("Object has been deserialized");
		}

		catch (IOException ex) {
			System.out.println(ex);
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}

		return net;
	}
}
