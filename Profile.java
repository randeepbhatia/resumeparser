import java.io.*;
import java.util.*;

/* Stores information for a user, such as resume content and skillset */

public class Profile {

	private HashMap<String, Integer> skills; //Skills and number of years exp
	private String email;
	private String name; 
	private HashMap<String, Integer> keywordMatch; //Matches keywords to occurrence rate
	private String resumeFilename;
	private String skillsFilename;

	private boolean hasResume = false;
	private boolean hasSkills = false;

	private double longitude;
	private double latitude;


	public boolean hasResume() {
		return hasResume;
	}

	public boolean hasSkills() {
		return hasSkills;
	}

	public String getSkillsFilename() {
		return skillsFilename;
	}

	public String getResumeFilename() {
		return resumeFilename;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//Used if resume and skills are provided
	public Profile(String resumeFilename, String keywordsFilename, String skillsFilename, double latitude, double longitude) {

		ResumeAnalyzer resumeAnalyzer = new ResumeAnalyzer(resumeFilename, keywordsFilename);
		SkillsExtractor skillsExtractor = new SkillsExtractor(skillsFilename);

		keywordMatch = resumeAnalyzer.getKeywordDict();
		skills = skillsExtractor.getSkills();

		this.resumeFilename = resumeFilename;
		this.skillsFilename = skillsFilename;

		hasResume = true;
		hasSkills = true;

		this.latitude = latitude;
		this.longitude = longitude;

	}

	//Used if only resume is provided
	public Profile(String resumeFilename, String keywordsFilename, double latitude, double longitude) {

		ResumeAnalyzer resumeAnalyzer = new ResumeAnalyzer(resumeFilename, keywordsFilename);
		keywordMatch = resumeAnalyzer.getKeywordDict();

		this.resumeFilename = resumeFilename;

		hasResume = true;

		this.latitude = latitude;
		this.longitude = longitude;

	}

	//Used if only skillset is provided
	public Profile(String skillsFilename, double latitude, double longitude) {
		SkillsExtractor skillsExtractor = new SkillsExtractor(skillsFilename);
		skills = skillsExtractor.getSkills();

		hasSkills = true;
		this.skillsFilename = skillsFilename;

		this.latitude = latitude;
		this.longitude = longitude;
	}

}
