package KNNClassifier;

import java.util.Scanner;

public class Iris {
	
	public final float sepalLength;
	public final float sepalWidth;
	public final float petalLength;
	public final float petalWidth;
	public final String classifier;
	
	public Iris(Scanner s,boolean hasClass){
		sepalLength = s.nextFloat();
		sepalWidth = s.nextFloat();
		petalLength = s.nextFloat();
		petalWidth = s.nextFloat();
		if(hasClass) classifier = s.next();
		else classifier =null;
	}
	
	public String toString(){
		return sepalLength+" "+sepalWidth+" "+petalLength+" "+petalWidth+" "+classifier;
	}
	
	public double distanceFrom(Iris other,Iris range){
		if(other == null) return Double.POSITIVE_INFINITY;
		double sepalLengthDiff = Math.pow(sepalLength-other.sepalLength,2)/Math.pow(range.sepalLength,2);
		double sepalWidthDiff = Math.pow(sepalWidth-other.sepalWidth,2)/Math.pow(range.sepalWidth,2);
		double petalLengthDiff = Math.pow(petalLength-other.petalLength,2)/Math.pow(range.petalLength,2);
		double petalWidthDiff = Math.pow(petalWidth-other.petalWidth,2)/Math.pow(range.petalWidth,2);
		return Math.sqrt(sepalLengthDiff+sepalWidthDiff+petalLengthDiff+petalWidthDiff);
	}
}
