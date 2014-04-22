import java.util.*;
import java.io.*;

public class Matchmaker {


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

		return .4 * skillMatch + .3 * companyValue + .2 * companyDistanceMetric + .1 * resumeDescriptionMatch;

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

			Fortune500Ranking companyRanker = new Fortune500Ranking("Fortune500List");

			int companyRank = companyRanker.getRank(companyName);
			double companyDistance = ZipDistCalc.calcDistance(latitude, longitude, user.getLatitude(), user.getLongitude());

			String userResumeFilename = user.getResumeFilename();
			String userSkillsFilename = user.getSkillsFilename();

			double resumeDescriptionMatch = (new ResumeJDComparison(userResumeFilename, jdFilename)).percentMatch();
			double skillMatch = (new SkillsJDComparison(userSkillsFilename, jobSkillsFilename)).percentMatch();

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
