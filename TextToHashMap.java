import java.io.*;
import java.util.*;

/*Converts text to a hashmap of lowercase words to occurrences*/

public class TextToHashMap {

	public TextToHashMap(String filename) {
		this.filename = filename;
		this.analyze();
	}

	private String filename;
	private HashMap<String, Integer> wordDict; //Stores words and occurrences
	private HashSet<Character> specialChars = (new AnalyzationData()).getSpecialChars(); //Special characters that should be treated as part of a word

	public HashMap<String, Integer> getWordDict() {
		return wordDict;
	}

	private boolean isValidAndNotUpperCase(char c) {

		return ( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || specialChars.contains(c) );

	}

	private boolean isValidAndUpperCase(char c) {
		return c >= 'A' && c <= 'Z';
	}

	//Figures out keyword occurrence values
	private void analyze() {

		BufferedReader reader = null;

		try {
			
			reader = new BufferedReader(new FileReader(filename));
			HashMap<String, Integer> wordDict = new HashMap();

			char curChar = 0;
			int c = 0;
			String curString = "";
			boolean searchingForValid = true; //Trying to find a valid character to start string
			boolean reachedEnd = false; //Checks if we have reached end of file

			while ((c = reader.read()) != -1) {

				curChar = (char) c;

				if (isValidAndUpperCase(curChar) || isValidAndNotUpperCase(curChar)) {

					if (isValidAndUpperCase(curChar)) {

						curChar = Character.toLowerCase(curChar);

					}

					if (searchingForValid) {

						searchingForValid = false;

						curString = Character.toString(curChar);

					} else {

						curString += curChar;

					}

				} else if (!searchingForValid) {

					searchingForValid = true;

					if (!curString.equals("")) {

						if (wordDict.containsKey(curString)) {

							wordDict.put(curString, wordDict.get(curString) + 1);

						} else {

							wordDict.put(curString, 1);

						}

					}

				}

			}

			//Final case
			if (!curString.equals("")) {

				if (wordDict.containsKey(curString)) {

					wordDict.put(curString, wordDict.get(curString) + 1);

				} else {

					wordDict.put(curString, 1);

				}

			}

			this.wordDict = wordDict;


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

}
