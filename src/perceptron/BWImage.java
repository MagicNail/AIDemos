package perceptron;

import java.util.*;

public class BWImage {
	
	private boolean[][] data;
	private boolean value;
	
	public BWImage(Scanner sc){
		if(!sc.nextLine().trim().equals("P1")) throw new IllegalArgumentException();
		String comment = sc.nextLine().trim();
		if(comment.equals("#Yes")) value = true;
		else value = false;
		int width = sc.nextInt();
		int height = sc.nextInt();
		data = new boolean[width][height];
		String dat = sc.next();
		dat += sc.next();
		for(int i=0;i<width*height;i++){
			data[i%width][i/width] = dat.charAt(i) == '1' ? true : false;
		}
		if(sc.hasNextLine()) sc.nextLine();
	}
	
	public boolean getPixel(int x,int y){
		return data[x][y];
	}
	public int getWidth(){return data.length;}
	public int getHeight(){return data[0].length;}
	public boolean getValue(){return value;}
}
