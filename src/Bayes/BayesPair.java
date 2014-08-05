package Bayes;

public class BayesPair {
	
	private int True = 1;
	private int False = 1;
	
	public void addTrue(){
		True++;
	}
	public void addFalse(){
		False++;
	}
	
	public int getTrue(){
		return True;
	}
	public int getFalse(){
		return False;
	}
	public double getTrueFraction(){
		return ((double)True)/(True+False);
	}
	public double getFalseFraction(){
		return ((double)False)/(True+False);
	}
	
	public String toString(){
		return "("+True+","+(True+False)+")";
	}
}
