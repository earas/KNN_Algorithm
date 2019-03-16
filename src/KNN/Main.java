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

			addData(implement);

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
		// sc.close();
	}

	static void addData(Knnalgorithm implement) throws IOException {

		Main: while (true) {

			System.out.println("Do you want to add new data? Y/N");
			String answer = sc.nextLine();
			if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {

				System.out.println("Enter test dataset file name");
				String testfilename = sc.nextLine();
				implement.loadtestData(testfilename);
				System.out.println("Enter new features:");
				String line = sc.nextLine();
				String[] split = line.split(",");
				double[] feature = new double[split.length];
				for (int i = 0; i < split.length; i++) {
					feature[i] = Double.parseDouble(split[i]);
				}
				Set<String> unique = new HashSet<String>();
				unique = implement.findUniques();

				System.out.println("hint: Current labels: " + unique.toString());
				System.out.println("Enter new label:");

				String label = sc.nextLine();
				boolean check = false;
				for (String s : unique) {
					if (s.equalsIgnoreCase(label)) {
						check = true;
					}
				}
				if (check) {
					implement.setTest(feature, label);
					implement.writefile();
					System.out.println("new data added succesfully!");
					break Main;
				} else {
					System.out.println("are you sure to add new label? Y/N");
					String answer2 = sc.nextLine();
					if (answer2.equalsIgnoreCase("y") || answer2.equalsIgnoreCase("yes")) {
						implement.setTest(feature, label);
						implement.writefile();
						System.out.println("new data added succesfully!");
						break Main;

					} else if (answer2.equalsIgnoreCase("n") || answer2.equalsIgnoreCase("no")) {
						break Main;
					} else {
						System.out.println("unknown input");

					}

				}

			} else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
				implement.cleanStard();
				calculate(implement);
			} else {
				System.out.println("Unknown answer!");

				break Main;
			}
		}

	}

}
