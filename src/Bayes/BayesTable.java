package Bayes;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

public class BayesTable {
	
	public final int attributes;
	private BayesPair[] spam;
	private BayesPair[] nonSpam;
	
	public BayesTable(int attributes){
		this.attributes = attributes;
		spam = new BayesPair[attributes+1];
		nonSpam = new BayesPair[attributes+1];
		for(int i=0;i<attributes+1;i++){
			spam[i] = new BayesPair();
			nonSpam[i] = new BayesPair();
		}
	}
	
	public void add(String s) throws ParseException{
		Scanner sc = new Scanner(s);
		int[] values = new int[attributes];
		for(int i=0;i<attributes;i++){
			String c = sc.next();
			if(c.equals("1")) values[i]++;
			else if(!c.equals("0")) throw new ParseException(s,i);
		}
		String c = sc.next();
		//Branch for if training data item is spam
		if(c.equals("1")){
			for(int i=0;i<attributes;i++) {
				if(values[i] == 1)spam[i].addTrue();
				else spam[i].addFalse();
			}
			spam[attributes].addTrue();
			nonSpam[attributes].addFalse();
		}
		//Branch for not spam
		else if(c.equals("0")){
			for(int i=0;i<attributes;i++) {
				if(values[i] == 1)nonSpam[i].addTrue();
				else nonSpam[i].addFalse();
			}
			spam[attributes].addFalse();
			nonSpam[attributes].addTrue();
		}
		else throw new ParseException(s,s.length()-1);
	}
	
	public boolean evaluateSpam(Scanner sc){
		//Preparing values for processing
		int[] values = new int[attributes];
		for(int i=0;i<attributes;i++){
			String c = sc.next();
			if(c.equals("1")) values[i]++;
			else if(!c.equals("0")) throw new IllegalArgumentException();
		}
		if(sc.hasNext()) throw new IllegalArgumentException();
		//Calculate spam score
		double spamNeumerator = 1;
		for(int i=0;i<attributes;i++) {
			if(values[i] == 1) spamNeumerator *= spam[i].getTrueFraction();
			else spamNeumerator *= spam[i].getFalseFraction();
			
		}
		double spamDenominator = 1;
		for(int i=0;i<attributes;i++) {
			if(values[i] == 1) spamDenominator *= spam[i].getTrueFraction() + nonSpam[i].getTrueFraction();
			else spamDenominator *= spam[i].getFalseFraction() + nonSpam[i].getFalseFraction();
		}
		double spamScore = spamNeumerator/spamDenominator;
		//Calculate nonSpam score
		double nonSpamNeumerator = 1;
		for(int i=0;i<attributes;i++) {
			if(values[i] == 1) nonSpamNeumerator *= nonSpam[i].getTrueFraction();
			else nonSpamNeumerator *= nonSpam[i].getFalseFraction();
		}
		double nonSpamDenominator = 1;
		for(int i=0;i<attributes;i++) {
			if(values[i] == 1) nonSpamDenominator *= spam[i].getTrueFraction() + nonSpam[i].getTrueFraction();
			else nonSpamDenominator *= spam[i].getFalseFraction() + nonSpam[i].getFalseFraction();
		}
		double nonSpamScore = nonSpamNeumerator/nonSpamDenominator;
		//System.out.println(spamScore+" "+nonSpamScore);
		//System.out.println(spamScore/(spamScore+nonSpamScore));
		//System.out.println(nonSpamScore/(spamScore+nonSpamScore));
		return spamScore>nonSpamScore;
	}
	
	public void printSelf(){
		System.out.println("    Spam  NonSpam");
		for(int i=0;i<attributes+1;i++){
			System.out.printf("%8s %8s\n",spam[i],nonSpam[i]);
		}
	}
}
