import static org.junit.Assert.*;

import org.junit.Test;


public class Tests {

	@Test
	public void justResumeTest() {
		
		Profile user = new Profile("TestResume", "TestKeywords", 50, 100);
		
		Job googleJob = new Job("Google", "GoogleJobDescription", "GoogleSkills", 50, 100);
		Job caterpillarJob = new Job("Caterpillar", "CaterpillarJobDescription", "CaterpillarSkills", 49.2, 101.2);
		Job twitterJob = new Job("Twitter", "TwitterJobDescription", "TwitterSkills", 50.1, 100.1);

		Job[] jobList = {caterpillarJob, googleJob, twitterJob};

		jobList = (new Matchmaker()).sortJobs(user, jobList);

		System.out.println("Test of just Resume\n");
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
	
	@Test
	public void fullFunctionalityTest() {
		
		System.out.println("Tests everything\n");
		
		Profile user = new Profile("MyResume", "RaghavKeywords", "RaghavSkills", 70, 70);
		Job intelJob = new Job("Intel", "IntelJD", "IntelSkills", 69.9, 70.1);
		Job walmartJob = new Job("Wal-Mart Stores", "WalmartJD", "WalmartSkills", 65, 71);
		Job nasaJob = new Job("NASA", "NASAJD", "NASASkills", 70, 70.1);
		Job[] jobList = {intelJob, walmartJob, nasaJob};
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
	
	@Test
	public void justTestSkills() {
		
		System.out.println("Tests just skills\n");
		
		Profile user = new Profile("RaghavSkills", 70, 70);
		Job intelJob = new Job("Intel", "IntelJD", "IntelSkills", 69.9, 70.1);
		Job walmartJob = new Job("Wal-Mart Stores", "WalmartJD", "WalmartSkills", 65, 71);
		Job nasaJob = new Job("NASA", "NASAJD", "NASASkills", 70, 70.1);
		Job[] jobList = {intelJob, walmartJob, nasaJob};
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
	
	@Test
	public void fortune500Test() {
		Fortune500Ranking ranker = new Fortune500Ranking();
		assertEquals("Phillips 66 rank not 4", ranker.getRank("Phillips 66"), 4);
		assertEquals("Walmart rank not 1", ranker.getRank("Wal-Mart Stores"), 1);
	}
	
	@Test
	public void weightsSumTo1() {
		Matchmaker matchMaker = new Matchmaker();
		assertEquals(matchMaker.getCompanyValueWeight() + matchMaker.getDistanceWeight() + matchMaker.getResumeWeight() + matchMaker.getSkillsWeight(), 1, .00001);
	}
	
	

}
