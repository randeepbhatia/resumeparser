import java.lang.Math;
import java.lang.Double;

public class ZipDistCalc {

	//from: http://zips.sourceforge.net/
	public static double calcDistance(double latA, double longA, double latB, double longB)
	{
	  double theDistance = (Math.sin(Math.toRadians(latA)) *
	                        Math.sin(Math.toRadians(latB)) +
	                        Math.cos(Math.toRadians(latA)) *
	                        Math.cos(Math.toRadians(latB)) *
	                        Math.cos(Math.toRadians(longA - longB)));

	  return  (Math.toDegrees(Math.acos(theDistance))) * 69.09;
	} 

}
