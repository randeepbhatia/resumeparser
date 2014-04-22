import java.io.*;
import java.util.*;


//Stores relevant data
public class AnalyzationData {


	private HashSet<Character> specialChars = new HashSet<Character>(Arrays.asList('#', '+', '\'', '[', ']'));

	public HashSet<Character> getSpecialChars() {
		return specialChars;
	}

}
