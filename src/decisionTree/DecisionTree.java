package decisionTree;

import java.util.*;

public class DecisionTree {
	
	private List<String> categoryNames = new ArrayList<String>();
	private List<String> attributes = new ArrayList<String>();
	private List<Instance> instances = new ArrayList<Instance>();
	private DecisionTreeNode root;
	private String baseline;
	private double baselineProbability;
	private Map<String,Integer> attsToInt = new HashMap<String,Integer>();

	public DecisionTree(Scanner sc){
		//Load CategoryNames and Attributes
		String categories = sc.nextLine();
		Scanner sc2 = new Scanner(categories);
		while(sc2.hasNext()) categoryNames.add(sc2.next());
		String attribs = sc.nextLine();
		Scanner sc3 = new Scanner(attribs);
		while(sc3.hasNext()) attributes.add(sc3.next());
		for(int i=0;i<attributes.size();i++) attsToInt.put(attributes.get(i),i);
		//Load Training-Data file
		while(sc.hasNext()){
			String line = sc.nextLine();
			Scanner lnScan = new Scanner(line);
			instances.add(new Instance(lnScan.next(), lnScan));
			//System.out.println(instances.get(instances.size()-1));
		}
		//Calculate the 'baseline'
		int count1 = 0;
		int count2 = 0;
		for(Instance i:instances){
			if(i.getCategory().equals(categoryNames.get(0))) count1++;
			else count2++;
		}
		if(count1 > count2) {
			baseline = categoryNames.get(0);
			baselineProbability = ((double)count1)/(count1+count2);
		}
		else {
			baseline = categoryNames.get(1);
			baselineProbability = ((double)count2)/(count1+count2);
		}
		//Building the tree
		root = BuildTree(instances,attributes);
	}

	/**
	 * Recursive method for creating the Decision Tree from instances and attributes
	 * 
	 * @param instances
	 * @param attributes
	 * @return node
	 */
	private DecisionTreeNode BuildTree(List<Instance> instances,List<String> attributes){
		//if instances is empty
		if(instances.isEmpty()){
			//return a leaf node containing the name and probability of the overall
			//most probable class (ie, the ‘‘baseline’’ predictor)
			return new DecisionTreeNode(baseline,baselineProbability);
		}
		//if instances are pure
		if(isPure(instances)){
			//return a leaf node containing the name of the class of the instances
			//in the node and probability 1
			return new DecisionTreeNode(instances.get(0).getCategory(),1.0f);
		}
		//if attributes is empty
		if(attributes.isEmpty()){
			//return a leaf node containing the name and probability of the
			//majority class of the instances in the node (choose randomly
			//if classes are equal)
			return new DecisionTreeNode(majority(instances),majorityProbability(instances));
		}
		//else find best attribute:
		double bestSoFar = 0;
		String bestAttribute = null;
		List<Instance> bestTrueSet = null;
		List<Instance> bestFalseSet = null;
		//for each attribute
		for(String attribute:attributes){
			//separate instances into two sets:
			//instances for which the attribute is true, and
			//instances for which the attribute is false
			List<Instance> trueSet = new ArrayList<Instance>();
			List<Instance> falseSet = new ArrayList<Instance>();
			for(Instance i:instances){
				if(i.getAtt(attsToInt.get(attribute))) trueSet.add(i);
				else falseSet.add(i);
			}
			//compute purity of each set.
			double truePurity = majorityProbability(trueSet);
			double falsePurity = majorityProbability(falseSet);
			//if weighted average purity of these sets is best so far
			//System.out.println("P: "+truePurity+" "+falsePurity+" "+bestSoFar);
			if((truePurity+falsePurity)/2>bestSoFar){
				//bestAtt = this attribute
				bestAttribute = attribute;
				//bestInstsTrue = set of true instances
				bestTrueSet = trueSet;
				//bestInstsFalse = set of false instances
				bestFalseSet = falseSet;
			}
		}
		//build subtrees using the remaining attributes:
		List<String> newAtts = new ArrayList<String>(attributes);
		newAtts.remove(bestAttribute);
		//left = BuildNode(bestInstsTrue, attributes - bestAtt)
		DecisionTreeNode left = BuildTree(bestTrueSet,newAtts);
		//right = BuildNode(bestInstsFalse, attributes - bestAttr)
		DecisionTreeNode right = BuildTree(bestFalseSet,newAtts);
		//return Node containing (bestAtt, left, right)
		return new DecisionTreeNode(bestAttribute,left,right);
	}

	private boolean isPure(List<Instance> instances){
		String cat1 = instances.get(0).getCategory();
		for(int i=1;i<instances.size();i++) if(!instances.get(i).equals(cat1)) return false;
		return true;
	}

	private String majority(List<Instance> instances){
		int count1 = 0;
		int count2 = 0;
		String majority;
		for(Instance i:instances){
			if(i.getCategory().equals(categoryNames.get(0))) count1++;
			else count2++;
		}
		if(count1 > count2) majority = categoryNames.get(0);
		else majority = categoryNames.get(1);
		return majority;
	}

	private double majorityProbability(List<Instance> instances){
		if(instances.isEmpty()) return 1;
		int count1 = 0;
		int count2 = 0;
		double majorityProbability;
		for(Instance i:instances){
			if(i.getCategory().equals(categoryNames.get(0))) count1++;
			else count2++;
		}
		if(count1 > count2) majorityProbability = ((double)count1)/(count1+count2);
		else majorityProbability = ((double)count2)/(count1+count2);
		return majorityProbability;
	}

	public void printSelf(){
		root.printSelf(0);
	}
	
	public void evaluate(Scanner sc){
		//Ignoring Header
		sc.nextLine();
		sc.nextLine();
		//Load Training-Data file
		List<Instance> testInstances = new ArrayList<Instance>();
		int correct = 0;
		int liveCorrect = 0;
		int dieCorrect = 0;
		int total = 0;
		int liveTotal = 0;
		int dieTotal = 0;
		for(int j=0;sc.hasNext();j++){
			String line = sc.nextLine();
			Scanner lnScan = new Scanner(line);
			Instance instance = new Instance(lnScan.next(), lnScan);
			testInstances.add(instance);
			DecisionTreeNode n = root;
			for(int i=0;!n.isLeaf();i++){
				if(instance.getAtt(attsToInt.get(n.getAttribute()))) n = n.getLeft();
				else n = n.getRight();
			}
			if(n.getClassification().equals(instance.getCategory())) {
				correct++;
				if(n.getClassification().equals("live")) liveCorrect++;
				else dieCorrect++;
			}
			if(n.getClassification().equals("live")) liveTotal++;
			else dieTotal++;
			total++;
			//System.out.println("Test Case "+j+": "+n.getClassification()+", "+(n.getClassification().equals(instance.getCategory())));
			
		}
		System.out.println("Trained on "+instances.size()+" values");
		System.out.println("Tested "+total+" values");
		System.out.println("live: "+liveCorrect+"/"+liveTotal);
		System.out.println("die: "+dieCorrect+"/"+dieTotal);
		System.out.println();
		System.out.println("Accuracy:");
		System.out.println("Decision Tree Accuracy: "+(((double)correct)/total));
		System.out.println("Baseline Accuracy (live): "+(((double)liveTotal)/total));
	}
	
	
	/**
	 * Instances of data used by the tree
	 * 
	 * @author Jeff
	 *
	 */
	private class Instance {

	    private String category;
	    private List<Boolean> vals;

	    public Instance(String cat, Scanner s){
	      category = cat;
	      vals = new ArrayList<Boolean>();
	      while (s.hasNextBoolean()) vals.add(s.nextBoolean());
	    }

	    public boolean getAtt(int index){
	      return vals.get(index);
	    }

	    public String getCategory(){
	      return category;
	    }
	    
	    public String toString(){
	      StringBuilder ans = new StringBuilder(category);
	      ans.append(" ");
	      for (Boolean val : vals)
		ans.append(val?"true  ":"false ");
	      return ans.toString();
	    }
	}
}
