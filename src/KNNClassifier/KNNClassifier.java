package KNNClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The purpose of this program is to classify iris flower data from a 
 * table of properties into the known seperate species
 * 
 * @author Jeffrey Burns
 *
 */

public class KNNClassifier {
	
	private String trainingFile;
	private String testFile;
	private List<Iris> trainingData;
	private List<Iris> testData;
	private Iris range;
	private int K = 5;
	
	public KNNClassifier(String file1,String file2){
		//Read from training set
		trainingFile = "part1/iris-training.txt";
		if(file1 != null) trainingFile = file1;
		trainingData = new ArrayList<Iris>();
		try {
			Scanner sc = new Scanner(new File(trainingFile));
			while(sc.hasNext()){
				trainingData.add(new Iris(sc,true));
				//System.out.println(trainingData.get(trainingData.size()-1));
			}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		//Read from test set
		testFile = "part1/iris-test.txt";
		if(file2 != null) testFile = file2;
		testData = new ArrayList<Iris>();
		try {
			Scanner sc = new Scanner(new File(testFile));
			while(sc.hasNext()){
				testData.add(new Iris(sc,true));
			}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		//Calculate data range of training set
		double sepalLengthMax = 0;
		double sepalLengthMin = Double.POSITIVE_INFINITY;
		double sepalWidthMax = 0;
		double sepalWidthMin = Double.POSITIVE_INFINITY;
		double petalLengthMax = 0;
		double petalLengthMin = Double.POSITIVE_INFINITY;
		double petalWidthMax = 0;
		double petalWidthMin = Double.POSITIVE_INFINITY;
		for(Iris i:trainingData){
			if(i.sepalLength>sepalLengthMax) sepalLengthMax = i.sepalLength;
			if(i.sepalLength<sepalLengthMin) sepalLengthMin = i.sepalLength;
			if(i.sepalWidth>sepalWidthMax) sepalWidthMax = i.sepalWidth;
			if(i.sepalWidth<sepalWidthMin) sepalWidthMin = i.sepalWidth;
			if(i.petalLength>petalLengthMax) petalLengthMax = i.petalLength;
			if(i.petalLength<petalLengthMin) petalLengthMin = i.petalLength;
			if(i.petalWidth>petalWidthMax) petalWidthMax = i.petalWidth;
			if(i.petalWidth<petalWidthMin) petalWidthMin = i.petalWidth;
		}
		range = new Iris(new Scanner((sepalLengthMax-sepalLengthMin)+" "+(sepalWidthMax-sepalWidthMin)+" "+
				(petalLengthMax-petalLengthMin)+" "+(petalWidthMax-petalWidthMin)),false);
		//System.out.println(range);
		System.out.println("Accuracy: "+(testAccuracy()*100)+"%");
	}
	/**
	 * Reads input data from the console. Used for testing.
	 */
	private void readInput() {
		System.out.println("Awaiting input in format <SepalLength> <SepalWidth> <PetalLength> <PetalWidth>");
		Scanner sc = new Scanner(System.in);
		while(true){
			String input = sc.nextLine();
			System.out.println("Class: "+classify(new Iris(new Scanner(input),false)));
		}
	}
	
	/**
	 * Compares classifier results on the test data with the actual information in the test data.
	 * Returns a number between one and zero for success rate.
	 * 
	 * @return success rate
	 */
	private double testAccuracy(){
		int sucesses = 0;
		for(Iris i:testData){
			if(classify(i).equals(i.classifier)) {
				System.out.println(i+" success");
				sucesses++;
			}
			else System.out.println(i+" fail");
		}
		return ((double)sucesses)/testData.size();
	}
	
	/**
	 * Measures a new Iris against the training set to classify its type. Returns the type as a string.
	 * 
	 * @param iris to compare against training set
	 * @return iris type
	 */
	private String classify(Iris iris){
		Iris[] nearests = new Iris[K];
		double[] nearestValues = new double[K];
		for(int i=0;i<K;i++) nearestValues[i] = Double.POSITIVE_INFINITY;
		for(Iris i:trainingData){
			for(int j=0;j<K;j++){
				if(iris.distanceFrom(i,range)<nearestValues[j]){
					nearests[j] = i;
					nearestValues[j] = iris.distanceFrom(i,range);
					break;
				}
			}
		}
		Map<String,Integer> neighbours = new HashMap<String,Integer>();
		for(int i=0;i<K;i++){
			//System.out.println(nearests[i]);
			if(!neighbours.containsKey(nearests[i].classifier)) neighbours.put(nearests[i].classifier,1);
			else neighbours.put(nearests[i].classifier,neighbours.get(nearests[i].classifier)+1);
		}
		String ans = nearests[0].classifier;
		int max = 1;
		for(String s:neighbours.keySet()){
			if(neighbours.get(s)>max){
				max = neighbours.get(s);
				ans = s;
			}
		}
		return ans;
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
		KNNClassifier k = new KNNClassifier(file1,file2);
		//k.readInput();
	}

	

}
