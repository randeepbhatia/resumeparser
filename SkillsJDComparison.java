import java.io.*;
import java.util.*;

/* Checks Skills content with job description content */

public class SkillsJDComparison {

	private String skillsFilename;
	private String descriptionFilename;

	private double percentMatch;

	public SkillsJDComparison(String skillsFilename, String descriptionFilename) {

		this.skillsFilename = skillsFilename;
		this.descriptionFilename = descriptionFilename;
		
		this.analyze();

	} 

	public double percentMatch() {
		return percentMatch;
	}

	private void analyze() {

		HashMap<String, Integer> jobDict = (new TextToHashMap(descriptionFilename)).getWordDict();
		HashMap<String, Integer> skillDict = (new SkillsExtractor(skillsFilename)).getSkills();

		int numMatch = 0; //Number of resume phrases in job description.

		for (String phrase: skillDict.keySet()) {

			String[] words = phrase.split("\\s+"); //Gets all the words in a single phrase

			boolean match = true;

			//Checks if all the words in a phrase are present in the job description
			for (int i = 0; i < words.length; i++) {

				if (!jobDict.containsKey(words[i])) {
					match = false;
					break;
				}

			}

			if (match) {
				numMatch += 1;
			}



		}

		this.percentMatch = ((double) numMatch) / jobDict.size(); //Number of resume matched words divided by number of words in JD


	}

}
