/* KeywordExtracter.java
* Gets Keywords from document, and returns dictionary
* No need to call directly, use analyzer classes.
*
*/

import java.io.*;
import java.util.*;

public class KeywordExtractor {

	public KeywordExtractor(String filename) {
		this.fileName = filename;
		this.analyze();
	}

	private String fileName;
	private int maxLength = 0;
	private HashSet<Character> specialChars = (new AnalyzationData()).getSpecialChars(); //These characters are not treated as skippable.
	private HashMap<String, Integer> theKeywords;

	public HashSet<Character> getSpecialChars() {
		return specialChars;
	}

	//Used to determine how many length phrases to try in analyzers
	public int getMaxLength() {
		return maxLength;
	}

	public HashMap<String, Integer> getKeywords() {
		return theKeywords;
	}

	private void analyze() {

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(fileName));

			HashMap<String,Integer> keywords = new HashMap(); //Maps keywords to occurrence value

			char curChar = 0; //Stores current character we are on
			int c = 0; //Integer that stores bytes
			String curString = ""; //Current String up to this point
			int curPhraseLength = 1;
			int maxPhraseLength = 1; //Used for future checks

			//Iterates through all the characters
			while ((c = reader.read()) != -1) {

				curChar = (char) c;

				if (curChar == '\n') {

					//Adds word to dictionary
					if (!(curString.equals(""))) {
						keywords.put(curString, 0);
						curString = "";
						if (curPhraseLength > maxPhraseLength) {
							maxPhraseLength = curPhraseLength;
						}
						curPhraseLength = 1;
					}

				} else if (curChar == ' ' || curChar == '-') {

					curString += ' ';
					curPhraseLength += 1;

				} else if (curChar >= 'A' && curChar <= 'Z') {

					curString += Character.toLowerCase(curChar);

				} else if ( (curChar >= 'a' && curChar <= 'z') || (curChar >= '0' && curChar <= '9') || specialChars.contains(curChar) ) {

					curString += curChar;

				}

			}

			//Puts last word in
			if (!(curString.equals(""))) {
				keywords.put(curString, 0);
				curString = "";
				if (curPhraseLength > maxPhraseLength) {
					maxPhraseLength = curPhraseLength;
				}
			}



			if (maxPhraseLength > maxLength) {
				maxLength = maxPhraseLength;
			}
			theKeywords = keywords;

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
