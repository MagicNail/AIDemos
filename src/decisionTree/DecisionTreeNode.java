package decisionTree;

public class DecisionTreeNode {
	
	private String classification;
	private double probability;
	private String attribute;
	private DecisionTreeNode left;
	private DecisionTreeNode right;
	private boolean isLeaf;
	
	public DecisionTreeNode(String classification,double probability){
		this.classification = classification;
		this.probability = probability;
		isLeaf = true;
	}
	
	public DecisionTreeNode(String attribute,DecisionTreeNode left, DecisionTreeNode right){
		this.attribute = attribute;
		this.left = left;
		this.right = right;
		isLeaf = false;
	}
	public boolean isLeaf(){
		return isLeaf;
	}
	public void printSelf(int indent){
		for(int i=0;i<indent;i++) System.out.print(" ");
		if(isLeaf) {
			System.out.println("Class "+classification+", prob = "+probability);
			return;
		}
		System.out.println(attribute+" = True");
		left.printSelf(indent+4);
		for(int i=0;i<indent;i++) System.out.print(" ");
		System.out.println(attribute+" = False");
		right.printSelf(indent+4);
	}
	public DecisionTreeNode getLeft(){
		return left;
	}
	public DecisionTreeNode getRight(){
		return right;
	}
	public String getClassification(){
		return classification;
	}
	public String getAttribute(){
		return attribute;
	}
}
