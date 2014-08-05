package perceptron;

import java.util.*;

public class Perceptron {
	
	private List<Feature> features = new ArrayList<Feature>();
	private List<BWImage> images;
	private int[] learnedValues;
	private int[][] featureValues;
	private double[] weights;
	private Random random;
	
	public Perceptron(){
		random = new Random(12345);
		weights = new double[50];
		for(int i=0;i<50;i++){
			features.add(new Feature(random));
			//weights[i] = random.nextDouble()*2-1;
		}
		features.get(0).makeDummy();
	}
	public void Classify(List<BWImage> imgs){
		images = imgs;
		learnedValues = new int[imgs.size()];
		featureValues = new int[imgs.size()][features.size()];
		for(int i=0;i<imgs.size();i++){
			for(int j=0;j<features.size();j++){
				featureValues[i][j] = features.get(j).classify(imgs.get(i));
				//System.out.print(featureValues[i][j]);
			}
			//System.out.println();
		}
		int loopCount = 0;
		while(successRating() != 1.0 && loopCount < 1000){
			System.out.println("Round "+loopCount+": "+successRating());
			for(int i=0;i<imgs.size();i++){
				double sumWeight = 0;
				int[] featureVector = featureValues[i];
				for(int j=0;j<features.size();j++){
					sumWeight += weights[j] * featureValues[i][j];
				}
				learnedValues[i] = sumWeight > 0 ? 1 : 0;
				if(learnedValues[i] - (imgs.get(i).getValue()?1:0) == 0){
					
				}
				else if(learnedValues[i] - (imgs.get(i).getValue()?1:0) > 0){
					for(int j=0;j<features.size();j++){
						weights[j] -= featureVector[j];
					}
				}
				else{
					for(int j=0;j<features.size();j++){
						weights[j] += featureVector[j];
					}
				}
			}
			loopCount++;
		}
		System.out.println("End Success Rating: "+successRating());
		System.out.println("Weights: ");
		for(int i=0;i<weights.length;i++){
			System.out.println("Feature "+(i+1)+": "+weights[i]);
		}
	}
	
	private double successRating(){
		int matches = 0;
		for(int i=0;i<images.size();i++){
			if(learnedValues[i] == (images.get(i).getValue()?1:0)) matches++;
		}
		return ((double)matches)/images.size();
	}
	
	private boolean classify(int index){
		BWImage img = images.get(index);
		double matches = 0;
		for(int i=0;i<features.size();i++){
			if(features.get(i).classify(img) == 1) matches++;
			//featureValues[index][i] = features.get(i).classify(img) == 1;
		}
		return matches>24;
	}
}
