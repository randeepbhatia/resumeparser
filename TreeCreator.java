import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;

//Pick the most popular word
//Get all words distance of 1 away from it
//Get all words distance of 1 away from children of popular word
//Etc...
//Repeats until trees made using all elements

public class TreeCreator {

	private static String findMostFrequentWord(HashMap<String, Integer> wordToTotalOcc) {
		
		int maxRate = 0;
		String maxString = null;
		
		for (String word: wordToTotalOcc.keySet()) {
			if (wordToTotalOcc.get(word) > maxRate) {
				maxRate = wordToTotalOcc.get(word);
				maxString = word;
			}
		}
		
		return maxString;
		
	}
	
	public static Set<TreeNode> createTrees(Pair[] pairList) {
		
		HashMap<String, Integer> wordToTotalOcc = new HashMap();
		
		//Stores occurrence rate of each word
		for (int i = 0; i < pairList.length; i++) {
			
			//If word is 1st word of occurrence pair, adds word accordingly
			if (wordToTotalOcc.containsKey(pairList[i].getWord1())) {
				wordToTotalOcc.put(pairList[i].getWord1(), wordToTotalOcc.get(pairList[i].getWord1()) + pairList[i].getOccRate());
			} else {
				wordToTotalOcc.put(pairList[i].getWord1(), pairList[i].getOccRate());
			}
			
			//If word is 2nd word of occurrence pair, adds word accordingly
			if (wordToTotalOcc.containsKey(pairList[i].getWord2())) {
				wordToTotalOcc.put(pairList[i].getWord2(), wordToTotalOcc.get(pairList[i].getWord2()) + pairList[i].getOccRate());
			} else {
				wordToTotalOcc.put(pairList[i].getWord2(), pairList[i].getOccRate());
			}
			
		}
		
		Set<TreeNode> treeSet = new HashSet<TreeNode>(); //Set of trees to return at end
		HashSet<Pair> pairListSet = new HashSet<Pair>(Arrays.asList(pairList)); //Set of pairs created from pair list
		
		//Keep making trees until out of words
		while (wordToTotalOcc.keySet().size() > 0) {
			
			boolean updated = true;
			TreeNode root = new TreeNode(findMostFrequentWord(wordToTotalOcc)); //Most common word
			Set<Set<TreeNode>> setOfChildrenSets = new HashSet<Set<TreeNode>>(); //List of all children at that level
			Set<TreeNode> tempchildren = new HashSet<TreeNode>(); 
			tempchildren.add(root); //0th level child is just root
			setOfChildrenSets.add(tempchildren); //Just the root for now
			wordToTotalOcc.remove(root.getName()); //Removes root as a used node
			Set<Pair> usedWords = new HashSet<Pair>(); //Pairs to remove after gone through
			
			//Adds new nodes
			while (updated) { //As long as last iteration was updated, keep trying to update
				
				updated = false;
				
				for (Set<TreeNode> children : setOfChildrenSets) { //Goes through all children sets at that level
			
					for (TreeNode child : children) { //Goes through each child in each set

						for (Pair pair: pairListSet) { //Checks each pair in the set
							
							
							if (pair.getWord1().equals(child.getName())) { //Checks if 1st word corresponds to child
							
								if (wordToTotalOcc.containsKey(pair.getWord2())) { //Makes word 2 a child if not already used
									wordToTotalOcc.remove(pair.getWord2()); //Removes word from dictionary to indicate already used
									child.addChild(new TreeNode(pair.getWord2())); //Makes word 2 a child of word 1
									updated = true;
								}
								usedWords.add(pair);

							} else if (pair.getWord2().equals(child.getName())) { //Checks if 2nd word corresponds to current child
								
								if (wordToTotalOcc.containsKey(pair.getWord1())) { //Makes word 1 a child if not already used
									
									wordToTotalOcc.remove(pair.getWord1()); //Removes word from dictionary to indicate already used
									child.addChild(new TreeNode(pair.getWord1())); //Makes word 1 a child of word 2
									updated = true;
								}
								usedWords.add(pair);
							}
						}
						for (Pair pair: usedWords) {
							pairListSet.remove(pair);
						}
					}	
				}
			
				//Updates children nodes for next level
				Set<Set<TreeNode>> newSetOfChildrenSets = new HashSet<Set<TreeNode>>();
				for (Set<TreeNode> children: setOfChildrenSets) { //Goes through all children sets at current level
				
					for (TreeNode child: children) {
					
						newSetOfChildrenSets.add(child.getChildren()); //Adds children of every child for that level
					
					}
				
				}
				setOfChildrenSets = newSetOfChildrenSets;
			}
		
			treeSet.add(root); //Adds tree to items to return
		}
		
		return treeSet;
		
	}
	
}
