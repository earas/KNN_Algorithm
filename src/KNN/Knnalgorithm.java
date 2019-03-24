package KNN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/*
* Knn algorithm implementation
*/

public class Knnalgorithm {

	// created lists for storing training and testing datasets label and features.
	Scanner sc = new Scanner(System.in);
	private List<double[]> trainfeatures = new ArrayList<>();
	private List<String> trainlabel = new ArrayList<>();
	private List<double[]> testfeatures = new ArrayList<>();
	private List<String> testlabel = new ArrayList<>();
	private Set<String> unique = new HashSet<String>();
	private Map<String, Integer> countTopLabel = new HashMap<String, Integer>();
	int knn_value = 1;
	int totalNumberOfLabel = 0;

	void setTest(double[] myfeature, String myLabel) throws IOException {

		BufferedWriter pw = new BufferedWriter(new FileWriter("TestLabel.txt"));
		testfeatures.add(myfeature);
		testlabel.add(myLabel);
		pw.write(myLabel);
		pw.close();
		totalNumberOfLabel++;

	}

	public Set findUniques() {

		return unique;
	}

	void cleanStard() {
		trainfeatures.clear();
		trainlabel.clear();
		testlabel.clear();
		testfeatures.clear();
		unique.clear();
		totalNumberOfLabel = 0;
		countTopLabel.clear();

	}

	/*
	 * loading training data and extracting features and label for training dataset
	 */
	void loadtrainData(String filename) throws NumberFormatException, IOException {

		File file = new File(filename);

		try {
			BufferedReader readFile = new BufferedReader(new FileReader(file));
			String line;
			while ((line = readFile.readLine()) != null) {

				String[] split = line.split(",");
				double[] feature = new double[split.length - 1];
				for (int i = 0; i < split.length - 1; i++)
					feature[i] = Double.parseDouble(split[i]);
				trainfeatures.add(feature);
				trainlabel.add(split[feature.length]);
			}
			unique.addAll(trainlabel);
			readFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/*
	 * loading testing data and extracting features and label for training dataset
	 * 
	 */
	void loadtestData(String testfilename) throws NumberFormatException, IOException {

		File testfile = new File(testfilename);

		try {
			BufferedReader testreadFile = new BufferedReader(new FileReader(testfile));
			PrintWriter pw = new PrintWriter("TestLabel.txt");
			String testline;
			while ((testline = testreadFile.readLine()) != null) {

				String[] split = testline.split(",");
				double[] feature = new double[split.length - 1];
				for (int i = 0; i < split.length - 1; i++) {
					feature[i] = Double.parseDouble(split[i]);
				}
				testfeatures.add(feature);
				testlabel.add(split[feature.length]);
				// writing original label for test data to file and counting number of label for
				// accuracy.
				pw.println(split[feature.length]);
				totalNumberOfLabel++;

			}
			pw.close();
			testreadFile.close();
			// unique.addAll(trainlabel);

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// Based on user input, calling algorithm to calculate distance
	// this method to calculate distance and writing output to file.

	void Distance() throws FileNotFoundException {
		DistanceFormula euclidean = new DistanceFormula();
		Iterator<double[]> testITR = testfeatures.iterator();
		PrintWriter pw = new PrintWriter("Result.txt");
		while (testITR.hasNext()) {
			double testF[] = testITR.next();
			Iterator<double[]> trainITR = trainfeatures.iterator();
			int noOfobject = 0;
			ArrayList<DistanceAndLabel> ts = new ArrayList<>();
			while (trainITR.hasNext()) {
				double trainF[] = trainITR.next();
				double dist = 0;
				dist = euclidean.getDistance(trainF, testF);
				String trainFeat = trainlabel.get(noOfobject);
				DistanceAndLabel DfObject = new DistanceAndLabel(dist, trainFeat);
				ts.add(DfObject);
				noOfobject++;

			}

			Collections.sort(ts);

			/*
			 * counting top predicted label based on k value
			 */

			// implement data into unique label map
			for (String s : unique) {
				countTopLabel.put(s, 0);
			}

			// check first labels(till K number) and increase +1
			int flag = 0;
			while (flag < knn_value) {
				DistanceAndLabel s = ts.get(flag);
				String s1 = s.getLabel();

				countTopLabel.computeIfPresent(s1, (k, v) -> v + 1);
				flag++;

			}

			// find max labels
			int max = 0;
			String topLabel = null;
			for (Map.Entry<String, Integer> check : countTopLabel.entrySet()) {
				if (check.getValue() > max) {
					topLabel = check.getKey();
					max = check.getValue();

				}
				;
			}

			pw.println(topLabel);
			System.out.println(topLabel + " " + max);
			countTopLabel.clear();

			/*
			 * counting label and selecting highest label count as prediction label and
			 * writing to output file.
			 */

		}
		pw.close();
	}

	void getKValue() {

		System.out.println("Enter the K value of KNN ");
		knn_value = sc.nextInt();
		if (knn_value > 50) {
			System.out.println("K Value is out of range.");
			getKValue();
		}

	}

	/*
	 * Calculating accuracy for model based Euclidean distance.
	 */
	void accuracy() throws IOException {
		int count = 0;
		File file = new File("Result.txt");

		BufferedReader rf = new BufferedReader(new FileReader(file));
		BufferedReader label = new BufferedReader(new FileReader(new File("TestLabel.txt")));
		String s = rf.readLine();
		while (s != null) {
			String lab = label.readLine();
			if (s.equalsIgnoreCase(lab)) {

			} else {
				count++;

			}

			s = rf.readLine();
		}

		System.out.println("Accuracy is: " + ((float) 100 - (((float) count / totalNumberOfLabel) * 100)) + "%");
		rf.close();
		label.close();
	}

}
