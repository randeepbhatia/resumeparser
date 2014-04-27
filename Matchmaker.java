import java.util.*;


public class Matchmaker {

	
	private double skillsWeight = .4;
	private double companyValueWeight = .3;
	private double distanceWeight = .2;
	private double resumeWeight = .1;
	
	public double getSkillsWeight() {
		return skillsWeight;
	}
	
	public double getCompanyValueWeight() {
		return companyValueWeight;
	}
	
	public double getDistanceWeight() {
		return distanceWeight;
	}
	
	public double getResumeWeight() {
		return resumeWeight;
	}

	//Converts rank to a double between 0 and 1, 1 being the best
	private double rankToCompanyWeight(int rank) {

		if (rank <= 0) {
			return 0;
		} else {
			return (501 - rank) / 500.0;
		}
	} 

	private double jobMetric(double skillMatch, double companyValue, double companyDistanceMetric, double resumeDescriptionMatch) {

		//Weights should add to 1. Change accordingly
		//All inputs to this function should be between 0 and 1 based on how good each one is (0 being bad, 1 being good)

		return skillsWeight * skillMatch + companyValueWeight * companyValue + distanceWeight * companyDistanceMetric + resumeWeight * resumeDescriptionMatch;

	}

	//For dice
	private double jobMetric(double skillMatch, double companyDistanceMetric) {
		double scalingFactor = 1.0/(skillsWeight + distanceWeight);
		return scalingFactor * (skillsWeight * skillMatch + distanceWeight * companyDistanceMetric);
	}


	//If more than 100 miles away, give it a distance ranking of 0 b/c too far
	//Otherwise, map from 0 to 1
	private double distanceMetric(double distance) {

		if (distance > 100) {
			return 0;
		} else {
			return (100 - distance) / 100.0;
		}

	}

	public Job[] sortJobs(Profile user, Job[] listOfJobs) {

		for (int i = 0; i < listOfJobs.length; i++) {

			Job job = listOfJobs[i];
			String jdFilename = job.getJDFilename();
			String jobSkillsFilename = job.getJobSkillsFilename();
			String companyName = job.getCompanyName();
			double latitude = job.getLatitude();
			double longitude = job.getLongitude();

			Fortune500Ranking companyRanker = new Fortune500Ranking();

			int companyRank = companyRanker.getRank(companyName);
			double companyDistance = ZipDistCalc.calcDistance(latitude, longitude, user.getLatitude(), user.getLongitude());

			String userResumeFilename = null;

			if (user.hasResume()) {
				userResumeFilename = user.getResumeFilename();
			} 

			String userSkillsFilename = null;

			if (user.hasSkills()) {
				userSkillsFilename = user.getSkillsFilename();
			}

			double resumeDescriptionMatch;
			double skillMatch;

			if (user.hasResume()) {
				resumeDescriptionMatch = (new ResumeJDComparison(userResumeFilename, jdFilename)).percentMatch();
			} else {
				resumeDescriptionMatch = 0;
			}

			if (user.hasSkills()) {
				skillMatch = (new SkillsJDComparison(userSkillsFilename, jobSkillsFilename)).percentMatch();
			} else {
				skillMatch = 0;
			}


			job.setRank(jobMetric(skillMatch, rankToCompanyWeight(companyRank), distanceMetric(companyDistance), resumeDescriptionMatch));

			job.setSkillsMatch(skillMatch);
			job.setDescriptionMatch(resumeDescriptionMatch);
			job.setDistance(distanceMetric(companyDistance));
			job.setCompanyValue(rankToCompanyWeight(companyRank));


		}

		Arrays.sort(listOfJobs);

		return listOfJobs;




	}


}
