import java.io.*;
import java.util.*;

/* Analyzes the given fortune 500 Table, and assings a ranking to a company based on how good it is.*/

public class Fortune500Ranking {

	private HashMap<String, Integer> companyToRanking = new HashMap();
	private String fortuneFilename;

	public Fortune500Ranking(String fortuneFilename) {

		this.fortuneFilename = fortuneFilename;
		this.analyze();

	}

	public HashMap<String, Integer> getRankings() {
		return companyToRanking;
	}

	public int getRank(String companyName) {

		if (companyToRanking.containsKey(companyName)) {
			return companyToRanking.get(companyName);
		} else {
			return 0;
		}

	}

	//Analyzes a line from the table and gets the name of the company
	private String companyName(String line, int companyNumber) {

		int startPosition; //Offset where company starts
		if (companyNumber > 0 && companyNumber < 10) {
			startPosition = 1;
		} else if (companyNumber >= 10 && companyNumber < 100) {
			startPosition = 2;
		} else {
			startPosition = 3;
		}

		if (companyNumber == 4) {
			return "Phillips 66"; //Special case
		}

		int endPosition = line.length() - 1;
		char curChar = 0;

		while (endPosition > 0) {

			curChar = line.charAt(endPosition);
			if (!( (curChar >= '0' && curChar <= '9') || curChar == '.' || curChar == ',' || curChar == '-')) {

				if (line.charAt(endPosition + 1) == '.') {
					endPosition = endPosition + 1;
				} 

				break;

			} else {
				endPosition -= 1;
			}

		}

		return line.substring(startPosition, endPosition + 1);

	}

	private void analyze() {

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(fortuneFilename));

			char curChar = 0;
			int c = 0;
			String curLine = "";

			int companyNumber = 1;

			while ((c = reader.read()) != -1) {

				curChar = (char) c;

				if (curChar == '\n') {

					if (!curLine.equals("")) {

						String company = companyName(curLine, companyNumber);
						curLine = "";

						companyToRanking.put(company, companyNumber);
						companyNumber++;

					}

				} else {

					curLine += curChar;

				}


			}

			if (!curLine.equals("")) {
				String company = companyName(curLine, companyNumber);
				curLine = "";
				companyToRanking.put(company, companyNumber);

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
