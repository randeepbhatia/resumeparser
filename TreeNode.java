import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class TreeNode implements Iterable<TreeNode> {

	  private Set<TreeNode> children;

	  private String name;
	  
	  
	  public TreeNode(String name) {
		this.name = name;
	    children = new HashSet<TreeNode>();
	  }
	  
	  public String getName() {
		  return name;
	  }

	  public boolean addChild(TreeNode n) {
	    return children.add(n);
	  }

	  public boolean removeChild(TreeNode n) {
	    return children.remove(n);
	  }
	  
	  public Set<TreeNode> getChildren() {
		  return children;
	  }

	  public Iterator<TreeNode> iterator() {
	    return children.iterator();
	  }
		
	  public static void main(String[] argv) {
		  TreeNode node1 = new TreeNode("Test");
		  System.out.println(node1.getName());
	  }
	  
	  public String toString() {
		  	String returnString = "";
		  	int level = 0;
			Set<Set<TreeNode>> setOfChildrenSets = new HashSet<Set<TreeNode>>(); //List of all children at that level
			Set<TreeNode> tempchildren = new HashSet<TreeNode>(); 
			tempchildren.add(this); //0th level child is just root
			setOfChildrenSets.add(tempchildren); //Just the root for now
			
			boolean thingsLeft = true;
			
			while (thingsLeft) {
				
				thingsLeft = false;
				
				for (Set<TreeNode> children : setOfChildrenSets) { //Goes through all children sets at that level
				
					for (TreeNode child : children) { //Goes through each child in each set
						
						thingsLeft = true;
						returnString = returnString + child.getName() + " " + level + "\n";
						
					}
				}
				
				level++;
				Set<Set<TreeNode>> newSetOfChildrenSets = new HashSet<Set<TreeNode>>();
				for (Set<TreeNode> children: setOfChildrenSets) { //Goes through all children sets at current level
				
					for (TreeNode child: children) {
					
						newSetOfChildrenSets.add(child.getChildren()); //Adds children of every child for that level
					
					}
				
				}
				setOfChildrenSets = newSetOfChildrenSets;
				
			}
			return returnString;
	  }
	  
}
