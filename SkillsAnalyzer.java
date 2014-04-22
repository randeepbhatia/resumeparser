import java.io.*;
import java.util.*;

public class SkillsAnalyzer {


	private String filename; //Filename for Skills file
	private HashMap<String, Double> keywords;

	private double multiplier = .25; //4 levels, so .25 for each


	public SkillsAnalyzer(String skillsFilename, String keywordsFilename) {

		this.filename = skillsFilename;
		KeywordExtractor extractor = new KeywordExtractor(keywordsFilename);
		keywords = hashIntToDouble(extractor.getKeywords());
		this.analyze();
	}

	public HashMap<String, Double> getKeywordDict() {
		return keywords;
	}

	public double computePercentage() {

		double sum = 0;

		Set<String> keywordList = keywords.keySet();
		for (String word: keywordList) {
			sum += keywords.get(word);
		}
		if (keywordList.size() == 0) {
			return 0;
		} else {
			return sum/keywordList.size();
		}


	}

	//Converts mapping to doubles
	private HashMap<String, Double> hashIntToDouble(HashMap<String, Integer> keywords) {

		HashMap<String, Double> newHashmap = new HashMap();
		for (String word : keywords.keySet()) {
			newHashmap.put(word, (double) keywords.get(word));
		}
		return newHashmap;

	}

	private double skillRelevance(int years) {
		if (years < 0) {
			return 0;
		} else if (years >= 0 && years < 2) {
			return multiplier;
		} else if (years >= 2 && years < 5) {
			return multiplier * 2;
		} else if (years >= 5 && years < 7) {
			return multiplier * 3;
		} else {
			return multiplier * 4;
		}
	}


	//Analyzes a line for skill and years of experience
	private ArrayList analyzeLine(String line) {

		ArrayList analysis = new ArrayList();

		//Gets year value
		int stringIndex = line.length() - 1;
		String years = "";
		while (stringIndex >= 0 && (line.charAt(stringIndex) >= '0' && line.charAt(stringIndex) <= '9' )) {
			years = Character.toString(line.charAt(stringIndex)) + years;
			stringIndex -= 1;
		}
		int yearValue = Integer.parseInt(years);

		//Gets skill
		String skill = line.substring(0, stringIndex).toLowerCase();
		analysis.add(yearValue);
		analysis.add(skill);
		return analysis;

	}

	private void analyze() {

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(filename));
			char curChar = 0;
			int c = 0;
			String curLine = "";

			while ( (c = reader.read()) != -1) {

				curChar = (char) c;
				if (curChar == '\n') {

					if (!curLine.equals("")) {
						ArrayList analysis = analyzeLine(curLine);
						curLine = "";

						int years = (Integer) analysis.get(0);
						String skill = (String) analysis.get(1);

						double skillMultiplier = skillRelevance(years);
						if (keywords.containsKey(skill) && keywords.get(skill) == 0) {
							keywords.put(skill, skillMultiplier);
						}
						
					}


				} else {
					curLine += curChar;
				}

			}

			if (!curLine.equals("")) {

				ArrayList analysis = analyzeLine(curLine);
				int years = (Integer) analysis.get(0);
				String skill = (String) analysis.get(1);
				double skillMultiplier = skillRelevance(years);
				if (keywords.containsKey(skill) && keywords.get(skill) == 0) {
					keywords.put(skill, skillMultiplier);
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

}
