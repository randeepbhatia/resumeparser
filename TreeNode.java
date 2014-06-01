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

	  public Iterator<TreeNode> iterator() {
	    return children.iterator();
	  }
		
	  public static void main(String[] argv) {
		  TreeNode node1 = new TreeNode("Test");
		  System.out.println(node1.getName());
	  }
	  
}
