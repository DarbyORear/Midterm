package co.grandcircus.midterm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {

	/**
	 * Get any valid integer.
	 */
	public static int getInt(Scanner scnr, String prompt) {
		// This approach uses exception handling.
		System.out.print(prompt);
		try {
			int num = scnr.nextInt();
			scnr.nextLine();
			return num;
		} catch (InputMismatchException e) {
			System.out.println("Enter a whole number, using digits.");
			scnr.nextLine();
			return getInt(scnr, prompt);
		}
	}

	/**
	 * Get any valid double.
	 */
	public static double getDouble(Scanner scnr, String prompt) {
		// This approach use "hasNext" look ahead.
		boolean isValid = false;
		do {
			System.out.print(prompt);
			isValid = scnr.hasNextDouble();
			if (!isValid) {
				scnr.nextLine();
				System.out.println("Enter a number, in digits.");
			}
		} while (!isValid);

		double number = scnr.nextDouble();
		scnr.nextLine();
		return number;
	}

	/**
	 * Get any string.
	 */
	public static String getString(Scanner scnr, String prompt) {
		// This approach uses exception handling.
		System.out.println(prompt);
		return scnr.nextLine();
	}

	/**
	 * Get any valid integer between min and max.
	 */
	public static int getInt(Scanner scnr, String prompt, int min, int max) {
		boolean isValid = false;
		int number;
		do {
			number = getInt(scnr, prompt);

			if (number < min) {
				isValid = false;
				System.out.println("The number must be at least " + min);
			} else if (number > max) {
				isValid = false;
				System.out.println("The number must not be larger than " + max);
			} else {
				isValid = true;
			}

		} while (!isValid);
		return number;
	}

	/**
	 * Get any valid double between min and max.
	 */
	public static double getDouble(Scanner scnr, String prompt, double min, double max) {
		boolean isValid = false;
		double number;
		do {
			number = getDouble(scnr, prompt);

			if (number < min) {
				isValid = false;
				System.out.println("The number must be at least " + min);
			} else if (number > max) {
				isValid = false;
				System.out.println("The number must not be larger than " + max);
			} else {
				isValid = true;
			}

		} while (!isValid);
		return number;
	}

	/**
	 * Get a string of input from the user that must match the given regex.
	 */
	public static String getStringMatchingRegex(Scanner scnr, String prompt, String regex) {
		boolean isValid = false;
		String input;
		do {
			input = getString(scnr, prompt);

			if (input.matches(regex)) {
				isValid = true;
			} else {
				System.out.println("Input must match the appropriate format.");
				isValid = false;
			}

		} while (!isValid);
		return input;
	}

	/**
	 * Get a string of input from the user that must match the given regex.
	 */
	public static String getStringMatchingRegex(Scanner scnr, String prompt, String regex, boolean caseSensitive) {
		boolean isValid = false;
		String input;
		Matcher match;
		Pattern pattern;

		do {
			input = getString(scnr, prompt);

			if (caseSensitive) {
				pattern = Pattern.compile(regex);
				match = pattern.matcher(input);
			}

			else {
				pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				match = pattern.matcher(input);
			}

			if (match.find()) {
				isValid = true;
			} else {
				System.out.println("Invalid Entry. Please try again.");
				isValid = false;
			}

		} while (!isValid);
		return input;
	}

	/**
	 * Get a date from user input in the format mm/dd/yyyy
	 */
	public static Date getDate(Scanner scnr, String prompt) {
		SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		format.setLenient(false);
		boolean isValid = false;
		Date date = null;
		String input;
		do {
			// Step 1: get the raw string
			input = getString(scnr, prompt);
			// Step 2: convert it to a date
			try {
				// format.parse throws a ParseException, which is a checked exception and MUST
				// be caught.
				date = format.parse(input);
				// If exception doesn't occur, it's valid.
				isValid = true;
			} catch (ParseException ex) {
				// If exception occurs, it's invalid.
				isValid = false;
				System.out.println("Enter a valid date in format mm/dd/yyyy.");
			}

		} while (!isValid);
		return date;
	}
	
	public static LocalDate getLocalDate(Scanner scnr, String prompt) {
//		SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/DD/YYYY");
//		format.setLenient(false);
		boolean isValid = false;
		LocalDate date = null;
		String input;
		do {
			// Step 1: get the raw string
			input = getString(scnr, prompt);
			// Step 2: convert it to a date
			try {
				// format.parse throws a ParseException, which is a checked exception and MUST
				// be caught.
//				date = format.parse(input);
//				date = formatter.ofPattern("MM/dd/yyyy");
				date = LocalDate.parse(input, formatter);
				
				// If exception doesn't occur, it's valid.
				isValid = true;
			} catch (Exception ex) {
				// If exception occurs, it's invalid.
				isValid = false;
				System.out.println("Enter a valid date in format mm/dd/yyyy.");
			}

		} while (!isValid);
		return date;
	}
	
	public static boolean getBoolean(Scanner scnr, String prompt) {
		String response = getStringMatchingRegex(scnr, prompt, "false|true", false).toLowerCase();
		return Boolean.parseBoolean(response);
	}

}