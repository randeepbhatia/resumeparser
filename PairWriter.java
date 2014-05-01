import java.io.*;
import java.util.*;

/*Class used for storing pairs of words*/

public class PairWriter {
	
	private final static int COLD = 0;
	private final static int WARM = 1;
	private final static int HOT = 2;
	
	private final static double COLD_CUTOFF = .28;
	private final static double WARM_CUTOFF = .67;
	private final static double HOT_CUTOFF = .05;
	
	Pair[] pairs;
	
	public Pair[] getPairs() {
		return pairs;
	}
	
	public PairWriter(String[] resumeFilenames, String keywordFile) {
		
		this.recordPairs(resumeFilenames, keywordFile);
		
	}
	
	public void recordPairs(String[] resumeFilenames, String keywordFile) {
		
		PairContainer pairs = new PairContainer();
		
		for (int i = 0; i < resumeFilenames.length; i++) {
		
			HashMap<String, Integer> resumeWords = (new ResumeAnalyzer(resumeFilenames[i], keywordFile)).getKeywordDict();
			
			for (String word1: resumeWords.keySet()) {
			
				for (String word2: resumeWords.keySet()) {
				
					if (word1.compareTo(word2) < 0) {
					
						if (resumeWords.get(word1) > 0 && resumeWords.get(word2) > 0) {
						
							Pair newPair = new Pair(word1, word2);	
							pairs.addPair(newPair);
						}
					
					}
				
				}
			
			}
		}
		this.categorizePairs(pairs);
		
	}
	
	public void categorizePairs(PairContainer pairs) {
		
		
		
		HashSet<Pair> pairSet = pairs.getPairs();
		
		//Convert hashset to array
		Pair[] pairList = pairSet.toArray(new Pair[pairSet.size()]);
		
		
		Arrays.sort(pairList);
		
		int i;
		for (i = 0; i < COLD_CUTOFF * pairList.length; i++) {
			
			pairList[i].setRating(COLD);
			
		}
		for (; i < (COLD_CUTOFF + WARM_CUTOFF) * pairList.length; i++) {
			pairList[i].setRating(WARM);
		}
		for (; i < pairList.length; i++) {
			pairList[i].setRating(HOT);
		}
		
		this.pairs = pairList;
		
	}
	
	
	
	
	
}
