package Bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

/**
 * The purpose of this program is to classify emails as spam or not spam
 * based on a list of common words appearing in spam emails
 * 
 * @author Jeffrey Burns
 *
 */

public class NaiveBayesClassifier {
	
	private String trainingFile;
	private String testFile;
	private BayesTable trainingData;
	private BayesTable testData;
	private int attributes = 12;
	
	public NaiveBayesClassifier(String file1,String file2){
		//Read from training set
		trainingFile = "part2/spamLabelled.dat";
		if(file1 != null) trainingFile = file1;
		trainingData = new BayesTable(attributes);
		try {
			Scanner sc = new Scanner(new File(trainingFile));
			while(sc.hasNext()){
				trainingData.add(sc.nextLine());
				//System.out.println(trainingData.get(trainingData.size()-1));
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (ParseException e) {e.printStackTrace();}
		
		//Read from test set
		testFile = "part2/spamUnlabelled.dat";
		if(file2 != null) testFile = file2;
		try {
			Scanner sc = new Scanner(new File(testFile));
			while(sc.hasNext()){
				String next = sc.nextLine();
				boolean isSpam = trainingData.evaluateSpam(new Scanner(next));
				System.out.print(next+"    ");
				if(isSpam) System.out.println("Spam");
				else System.out.println("NonSpam");
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		//trainingData.printSelf();
		//System.out.println();
		//System.out.println("ANS: "+trainingData.evaluateSpam(new Scanner("0 1 1 1 0 0 1 0 0 1 1 1")));
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
		NaiveBayesClassifier k = new NaiveBayesClassifier(file1,file2);
	}
}
