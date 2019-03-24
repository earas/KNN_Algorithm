package KNN;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws NumberFormatException, IOException {

		Knnalgorithm implement = new Knnalgorithm();

		while (true) {
			System.out.println("[0] testing by a new input");
			System.out.println("[1] testing by a test file");
			System.out.println("[-1] exit");
			String input = sc.nextLine();
			int answer = Integer.parseInt(input);
			if (answer == 0) {
				addData(implement);
			} else if (answer == 1) {
				calculate(implement);
			} else if (answer == -1) {
				System.exit(0);
			} else {
				System.out.println("invalid value!");
			}
		}

	}

	static void calculate(Knnalgorithm implement) throws NumberFormatException, IOException {

		System.out.println("Enter training dataset file name");
		String trainfilename = sc.nextLine();
		System.out.println("Enter test dataset file name");
		String testfilename = sc.nextLine();

		implement.getKValue();
		implement.loadtrainData(trainfilename);
		implement.loadtestData(testfilename);
		implement.Distance();
		implement.accuracy();
		implement.cleanStard();
		// sc.close();
	}

	static void addData(Knnalgorithm implement) throws IOException {

		System.out.println("Enter new features:");
		String line = sc.nextLine();
		String[] split = line.split(",");
		double[] feature = new double[split.length];
		for (int i = 0; i < split.length; i++) {
			feature[i] = Double.parseDouble(split[i]);
		}
		implement.loadtrainData("train.txt");
		Set<String> unique = new HashSet<String>();
		unique = implement.findUniques();

		System.out.println("hint: Current labels: " + unique.toString());
		System.out.println("Enter new label:");
		String label = sc.nextLine();
		implement.setTest(feature, label);
		implement.getKValue();
		implement.Distance();
		implement.accuracy();
		implement.cleanStard();

	}

}