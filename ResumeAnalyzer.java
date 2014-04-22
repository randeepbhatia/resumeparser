import java.io.*;
import java.util.*;

public class ResumeAnalyzer {

	private String filename; //Of Resume
	private HashMap<String, Integer> keywords; //Stores keywords mapped to frequency
	private HashSet<Character> specialChars; //Characters such as +
	private int maxPhraseLength;

	public ResumeAnalyzer(String resumeFilename, String keywordFilename) {

		this.filename = resumeFilename;
		KeywordExtractor extractor = new KeywordExtractor(keywordFilename);
		specialChars = extractor.getSpecialChars();
		maxPhraseLength = extractor.getMaxLength();
		keywords = extractor.getKeywords();
		this.analyze();

	}

	public HashMap<String, Integer> getKeywordDict() {
		return keywords;
	}
	
	private boolean isValidAndNotUpperCase(char c) {

		return ( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || specialChars.contains(c) );

	}

	private boolean isValidAndUpperCase(char c) {
		return c >= 'A' && c <= 'Z';
	}

	//Checks through keywords to find match; returns null if not found
	private String keywordMatch(String[] wordQueue) {

		String curString = wordQueue[0];
		for (int i = 0; i < maxPhraseLength; i++) {

			if (keywords.containsKey(curString) ) {
				return curString;
			} else {
				if ( (i + 1 == maxPhraseLength) || wordQueue[i + 1] == null) {
					return null;
				} else {
					curString = curString + " " + wordQueue[i + 1];
				}
			}

		}
		return null;

	}

	//Figures out keyword occurrence values
	private void analyze() {

		BufferedReader reader = null;

		try {
			
			reader = new BufferedReader(new FileReader(filename));

			if (maxPhraseLength < 1) {
				return;
			}

			String[] wordQueue = new String[maxPhraseLength]; //Stores current words we will concatenate and check for

			char curChar = 0;
			int c = 0;
			String curString = "";
			boolean searchingForValid = true; //Trying to find a valid character to start string
			boolean reachedEnd = false; //Checks if we have reached end of file
			int i = 0;


			//Initializes word queue
			while (wordQueue[wordQueue.length - 1] == null)  {

				if ((c = reader.read()) == -1) {

					if (!curString.equals("")) {
						wordQueue[i] = curString;
					}
					reachedEnd = true;
					break;

				}

				curChar = (char) c;

				if (isValidAndNotUpperCase(curChar)) {

					if (searchingForValid) {
						searchingForValid = false;
						curString = Character.toString(curChar);
					} else {
						curString += curChar;
					}

				} else if (isValidAndUpperCase(curChar)) {

					if (searchingForValid) {
						searchingForValid = false;
						curString = Character.toString(Character.toLowerCase(curChar));
					} else {
						curString += Character.toLowerCase(curChar);
					}

				} else { //Not valid character

					if (!searchingForValid) {
						searchingForValid = true;
						wordQueue[i] = curString;
						i += 1;
 					}

				}

			}

			while (wordQueue[0] != null) {

				//Checks if match and updates dictionary accordingly
				String wordMatch = keywordMatch(wordQueue);
				if (wordMatch != null) {
					keywords.put(wordMatch, keywords.get(wordMatch) + 1);
				}

			
				for (int k = 0; k + 1 < wordQueue.length; k++) {
					wordQueue[k] = wordQueue[k + 1];
				}

				

				//Finds next word
				searchingForValid = true;
				boolean wordEnd = false;
				String newWord = "";
				if (!reachedEnd) {

					while (!wordEnd) {

						if ((c = reader.read()) == -1) {

							reachedEnd = true;
							break;

						}

						curChar = (char) c;

						if (isValidAndUpperCase(curChar)) {

							if (searchingForValid) {
								searchingForValid = false;
							}

							newWord += Character.toLowerCase(curChar);

						} else if (isValidAndNotUpperCase(curChar)) {

							if (searchingForValid) {
								searchingForValid = false;
							}
							newWord += Character.toLowerCase(curChar);

						} else {

							if (!searchingForValid) {
								wordEnd = true;
							}

						}

					}


					if (!newWord.equals("")) {
						wordQueue[wordQueue.length - 1] = newWord;
					}

				} else {
					wordQueue[wordQueue.length - 1] = null;
				}



			}




		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {

			}
		}
	}

	//Computes percentage of words found
	public double computePercentage() {

		int sum = 0;

		Set<String> keywordList = keywords.keySet();
		for (String word: keywordList) {
			if (keywords.get(word) > 0) {
				sum += 1;
			}
		}

		if (keywordList.size() == 0) {
			return 0;
		} else {
			return 100 * (((double) sum) / keywordList.size());
		}


	}

	


}
