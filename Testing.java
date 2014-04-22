import java.io.*;
import java.util.*;

//Shows how algorithm works
public class Testing {

	public static void main(String[] argv) {

		Profile user = new Profile("TestResume", "TestKeywords", "TestSkills", 50, 100);
		
		Job googleJob = new Job("Google", "GoogleJobDescription", "GoogleSkills", 50, 100);
		Job caterpillarJob = new Job("Caterpillar", "CaterpillarJobDescription", "CaterpillarSkills", 49.2, 101.2);
		Job twitterJob = new Job("Twitter", "TwitterJobDescription", "TwitterSkills", 50.1, 100.1);

		Job[] jobList = {caterpillarJob, googleJob, twitterJob};

		jobList = (new Matchmaker()).sortJobs(user, jobList);

		//Prints in improving order.
		for (int i = 0; i < jobList.length; i++) {
			System.out.println(jobList[i].getCompanyName());
		}

		for (int i = 0; i < jobList.length; i++) {
			Job job = jobList[i];
			System.out.println("Company Name: " + job.getCompanyName());
			System.out.println("Company Value: " + job.getCompanyValue());
			System.out.println("Distance: " + job.getDistance());
			System.out.println("SkillsMatch " + job.getSkillsMatch());
			System.out.println("Description Match " + job.getDescriptionMatch());
			System.out.println("Rank: " + job.getRank());
		}








	}

}
