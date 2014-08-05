package perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import decisionTree.DecisionTree;

/**
 * This program creates a perceptron to decide whether a black and 
 * white image is an 'X' or 'O'. 
 * 
 * 
 * @author Jeffrey Burns
 *
 */

public class PerceptronClassifier {
	
	private String file;
	private List<BWImage> images = new ArrayList<BWImage>();
	private Perceptron perceptron;
	
	public PerceptronClassifier(String filename){
		//Read from training set
		file = "part4/image.data";
		if(filename != null) file = filename;
		try {
			Scanner sc = new Scanner(new File(file));
			while(sc.hasNextLine()){
				images.add(new BWImage(sc));
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		perceptron = new Perceptron();
		perceptron.Classify(images);
	}
	
	/**
	 * Run program and wait for input to be classified
	 */
	public static void main(String[] args) {
		String file = null;
		if(args.length == 0) System.out.println("No arguments: using defaults");
		else if(args.length == 1){
			file = args[0];
		}
		else{
			System.out.println("usage: ImageFile");
		}
		PerceptronClassifier k = new PerceptronClassifier(file);
	}
}
