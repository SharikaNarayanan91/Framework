package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateRandomHelper {
	
	//Generate random Alpha Numeric string
	public static String GenerateRandomString(int length) {
		
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			// generate a random number between 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
	
	//Generate random string with date and time
	public static String GenerateRandomStringWithDateTime()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now).replaceAll("/", "").replaceAll(":", "").replaceAll(" ", "");
	}

	//Generate random numbers
	public static int GenerateRandomNumber(int length) {
		int desiredLength = length; 

        // Ensure the length is valid
        if (desiredLength <= 0) {
            System.out.println("Invalid length. Length should be greater than 0.");
        }

        // Calculate the range of numbers based on the desired length
        int minRange = (int) Math.pow(10, desiredLength - 1);
        int maxRange = (int) Math.pow(10, desiredLength) - 1;

        // Create a Random object
        Random random = new Random();
        // Generate a random number within the specified range
        int randomWithSpecificLength = random.nextInt(maxRange - minRange + 1) + minRange;

        return randomWithSpecificLength;
	}

	//Generate random alpha string
	public static String GenerateRandomAlphaString(int length) {
		// chose a Character random from this String
		String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = (int) (AlphaString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaString.charAt(index));
		}
		return sb.toString();
	}
}
