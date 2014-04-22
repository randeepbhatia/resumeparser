import java.io.*;
import java.util.*;

/* Stores all the information for a job */

public class Job implements Comparable<Job>{

	private double latitude;
	private double longitude;

	private String companyName;

	private String jdFilename; //Stores job description file name
	private String jobSkillsFilename; //Stores list of skills in file

	private double jobRank = 0; //Stores rank of the job

	private double companyValue = 0;
	private double distance = 0;
	private double descriptionMatch = 0;
	private double skillsMatch = 0;

	public void setCompanyValue(double value) {
		companyValue = value;
	}

	public double getCompanyValue() {
		return companyValue;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public double getSkillsMatch() {
		return skillsMatch;
	}

	public void setSkillsMatch(double skillsMatch) {
		this.skillsMatch = skillsMatch;
	}

	public double getDescriptionMatch() {
		return descriptionMatch;
	}

	public void setDescriptionMatch(double descriptionMatch) {
		this.descriptionMatch = descriptionMatch;
	}


	public void setRank(double rank) {

		this.jobRank = rank;

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getJDFilename() {
		return jdFilename;
	}

	public String getJobSkillsFilename() {
		return jobSkillsFilename;
	}

	public String getCompanyName() {
		return companyName;
	}

	public double getRank() {
		return this.jobRank;
	}

	public Job(String companyName, String jobDescriptionFilename, String jobSkillsFilename, double latitude, double longitude) {

		this.latitude = latitude;
		this.longitude = longitude;
		this.companyName = companyName;
		this.jdFilename = jobDescriptionFilename;
		this.jobSkillsFilename = jobSkillsFilename;

	}

	public int compareTo(Job other) {
		return ((Double) (this.jobRank)).compareTo(other.getRank());
	}

}
