import java.util.HashMap;
import java.util.HashSet;



/*Used to get pairs of words
 * Handles storage of pairs
 */



public class PairContainer {

	private HashMap<String, HashMap<String, Integer>> wordMap = new HashMap(); //Maps word to a hashmap of words to occ rate

	
	//Has occ rates
	public HashSet<Pair> getPairs() {
		
		HashSet<Pair> pairs = new HashSet();
		
		for (String word1: wordMap.keySet()) {
			
			for (String word2: wordMap.get(word1).keySet()) {
				
				if (word1.compareTo(word2) < 0) {
					
					Pair newPair = new Pair(word1, word2, wordMap.get(word1).get(word2));
					pairs.add(newPair);
					
				}
				
			}
			
		}
		
		return pairs;
		
	}
	

	
	public void addPair(Pair pair) {

		String word1 = pair.getWord1();
		String word2 = pair.getWord2();
		
		//Adds a mapping from both word1 to word2 and word2 to word1
		//No point in mapping a word to itself

		if (word1.equals(word2)) {
			return;
		}


		if (!wordMap.containsKey(word1)) {

			HashMap<String, Integer> tempMap = new HashMap();
			
			tempMap.put(word2, 1);

			wordMap.put(word1, tempMap);

		} else {

			HashMap<String, Integer> oldMap = wordMap.get(word1);

			if (oldMap.containsKey(word2)) {

				oldMap.put(word2, oldMap.get(word2) + 1);

			} else {

				oldMap.put(word2, 1);

			}

			wordMap.put(word1, oldMap);

		}

		if (!wordMap.containsKey(word2)) {

			HashMap<String, Integer> tempMap = new HashMap();

			tempMap.put(word1, 1);
	
			wordMap.put(word2, tempMap);

		} else {

			HashMap<String, Integer> oldMap = wordMap.get(word2);

			if (oldMap.containsKey(word1)) {

				oldMap.put(word1, oldMap.get(word1) + 1);

			} else {

				oldMap.put(word1, 1);

			}

			wordMap.put(word2, oldMap);

		}

	}


}
