
/*Pair object class
 * Stores a pair of word1, word2, and the occurrence rate of the pair
 */

public class Pair implements Comparable {

	private String word1;
	private String word2;
	
	private int occRate = 0;
	
	private final static int COLD = 0;
	private final static int WARM = 1;
	private final static int HOT = 2;
	
	private int rating  = 0;
	
	public String getWord1() {
		return word1;
	}
	
	public String getWord2() {
		return word2;
	}
	
	public Pair(String word1, String word2) {
		
		this.word1 = word1;
		this.word2 = word2;
		
	}
	
	public Pair(String word1, String word2, int occRate) {
		this.word1 = word1;
		this.word2 = word2;
		this.occRate = occRate;
	}
	
	public void setOccRate(int occRate) {
		this.occRate = occRate;
	}
	
	public int getOccRate() {
		return occRate;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getRating() {
		return rating;
	}


	@Override
	public int compareTo(Object o) {
		return ((Integer) occRate).compareTo( ((Pair) o).getOccRate());
	}
	
	
}
