import java.io.*;
import java.util.*;

/* Gets skills from file
 * Requires each line to have a skill phrase followed by number of years of experience
*/

public class SkillsExtractor {


	private String filename; //Filename for Skills file
	private HashMap<String, Integer> skills = new HashMap(); //Skills to years of exp
	private double multiplier = .25; //4 levels, so .25 for each


	public SkillsExtractor(String skillsFilename) {

		this.filename = skillsFilename;
		this.analyze();
	}


	public HashMap<String, Integer> getSkills() {
		return skills;
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

						skills.put(skill, years);
						
						
					}


				} else {
					curLine += curChar;
				}

			}

			if (!curLine.equals("")) {

				ArrayList analysis = analyzeLine(curLine);
				int years = (Integer) analysis.get(0);
				String skill = (String) analysis.get(1);
				skills.put(skill, years);
				


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
