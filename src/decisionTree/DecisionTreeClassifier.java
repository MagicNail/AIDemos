package decisionTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * The purpose of this program is to decide whether or not a person 
 * is likely to have heppatitis or not based on their symptoms 
 * analysed against past cases by creating a decision tree
 * 
 * 
 * @author Jeffrey Burns
 *
 */

public class DecisionTreeClassifier {
	
	private String trainingFile;
	private String testFile;
	private DecisionTree trainingData;
	
	public DecisionTreeClassifier(String file1,String file2){
		//Read from training set
		trainingFile = "part3/hepatitis-training.data";
		if(file1 != null) trainingFile = file1;
		try {
			Scanner sc = new Scanner(new File(trainingFile));
			trainingData = new DecisionTree(sc);
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		
		//Read from test set
		testFile = "part3/hepatitis-test.data";
		if(file2 != null) testFile = file2;
		try {
			Scanner sc = new Scanner(new File(testFile));
			trainingData.evaluate(sc);
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		trainingData.printSelf();
	}
	
	/**
	 * Run program and wait for input to be classified
	 */
	public static void main(String[] args) {
		String file1 = null;
		String file2 = null;
		if(args.length == 0) System.out.println("No arguments: using defaults");
		else if(args.length == 2){
			file1 = args[0];
			file2 = args[1];
		}
		else{
			System.out.println("usage: training-set test-set");
		}
		DecisionTreeClassifier k = new DecisionTreeClassifier(file1,file2);
	}
}
