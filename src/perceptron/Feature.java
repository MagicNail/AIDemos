package perceptron;

import java.util.Random;

public class Feature {
	
	private int[] row = new int[4];
	private int[] col = new int[4];
	private boolean[] sgn = new boolean[4];
	private boolean dummy = false;
	
	public Feature(Random random){
		for(int i=0;i<4;i++){
			row[i] = (int)(random.nextDouble()*10);
			col[i] = (int)(random.nextDouble()*10);
			sgn[i] = random.nextBoolean();
		}
	}
	
	public void makeDummy(){dummy = true;}
	
	public int classify(BWImage img){
		if(dummy) return 1;
		int matches = 0;
		for(int i=0;i<4;i++){
			if(img.getPixel(row[i], col[i]) == sgn[i]) matches++;
		}
		return matches>2 ? 1 : 0;
	}
	
	public String toString(){
		return "row:{"+row[0]+","+row[1]+","+row[2]+","+row[3]+"} col:{"+
				col[0]+","+col[1]+","+col[2]+","+col[3]+"} sgn:{"+
				sgn[0]+","+sgn[1]+","+sgn[2]+","+sgn[3]+"}";
				
	}
}
