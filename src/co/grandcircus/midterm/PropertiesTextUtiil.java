package co.grandcircus.midterm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PropertiesTextUtiil {
	
	//Reads from file the longer way (for practice, don't need to use)
	public static ArrayList<Property> readFromFile_oldWay(String file) {
		//Defines variables
		ArrayList<Property> allRentals = new ArrayList<>();
		FileInputStream inFile = null;
		Scanner input = null;
		String line;

		//Creates resources to get input from file
		try {
			inFile = new FileInputStream(file);
			input = new Scanner(inFile);

			//while there is a line to read, will read line, convert to country, and add to list
			while (input.hasNext()) {
				line = input.nextLine();
				allRentals.add(convertLineToProperty(line));
			}
			//Catches exception if file is not found and prints error msg
		} catch (FileNotFoundException ex) {
			System.out.println("Sorry, file does not exist.");
		} finally {
			//closes the resources whether the reading was successful or not
			if (input != null) {
				input.close();
			}
			if (inFile != null) {
				try {
					inFile.close();
				} catch (IOException ex) {
					System.out.println("Sorry, failed to close stream.");
					ex.printStackTrace();
				}
			}
		}
		return allRentals;

	}

	public static ArrayList<Property> readFromFile_newWay(String filePath) {
		ArrayList<Property> allCountries = new ArrayList<>();
		String line;

		/*
		 * Uses new feature called try with resources, used as follows, which closes all
		 * resources automatically at the end Note the use of parenthesis (BEFORE THE
		 * BRACES) where the resources are stored
		 */
		try (
				FileInputStream inFile = new FileInputStream(filePath);
				Scanner input = new Scanner(inFile);
		) 
		{
			//while there is a line to be read, continues reading, converting to country, then adding to list
			while (input.hasNext()) {
				line = input.nextLine();
				allCountries.add(convertLineToProperty(line));
			}
			
			//Catches exception if file is not found
		} catch (FileNotFoundException ex) {
			System.out.println("Sorry, file does not exist");
			//Catches any other exceptions
		} catch (IOException ex) {
			System.out.println("Sorry, there was an error.");
			ex.printStackTrace();
		}

		return allCountries;

	}

	
	//Convert line from .txt file into a country
	public static Property convertLineToProperty(String line) {
		//Split string by separator into array of properties
		String[] pieces = line.split("[+][+]");
		
		//Assign, and convert as needed, each piece of array to corresponding location in country constructor
		String name = pieces[0];
		String continent = pieces[1];
		boolean visited = Boolean.parseBoolean(pieces[2]);
		Property country = new Property(name, type, location, amenities, price, available);
		
		return country;
	}

	public static String convertPropertyToLine(Property country) {
		String line = country.toString();
		return line;
	}
	
	//Creates new file path
	public static void createFilePath(String filePath) {
		//Converts the string path to an actual path object for uses with Files class
		Path path = Paths.get(filePath);
		
		//If the path does not exist, creates the path
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				System.out.println("File created at " + path.toAbsolutePath());
			} catch (IOException ex) {
				System.out.println("An error has occurred.");
				ex.getStackTrace();
			}
		}
	}

	public static void writeToFile(ArrayList<Property> allCountries, String newPath) {

		// Done the new/easy way with automatic resource closing
		try (
			// the false tells the code to overwrite the program as opposed to adding to it
			FileOutputStream outFile = new FileOutputStream(newPath, false);
			PrintWriter output = new PrintWriter(outFile);
		) {
			//Converts the countries to strings and writes them to a file
			for (Property country : allCountries) {
				output.println(convertPropertyToLine(country));
			}
			//Catches exception thrown when file is not found
		} catch (FileNotFoundException ex) {
			System.out.println("Sorry, the output file does not exist.");
			
			//Catches all other exceptions
		} catch (IOException ex) {
			System.out.println("Sorry, something unexpected occurred.");
			ex.printStackTrace();
		}
	}

	//Appends a single line to the file, instead of overwriting the whole thing
	public static void appendToFile(Property country, String newPath) {
		
		// Done the new/easy way with automatic resource closing
		try (
			// the false tells the code to overwrite the program as opposed to adding to it
			FileOutputStream outFile = new FileOutputStream(newPath, true);
			PrintWriter output = new PrintWriter(outFile);
		) 
		{
			output.println(convertPropertyToLine(country));
		} catch (FileNotFoundException ex) {
			System.out.println("Sorry, the output file does not exist.");
		} catch (IOException ex) {
			System.out.println("Sorry, something unexpected occurred.");
			ex.printStackTrace();
		}
	}

	/*
	 *create arraylist of original countries we add, then write those
	 * countries to file we will read from (starting countries) then use read from
	 * method to read from that file to output list of countries when we print the
	 * menu nd they choose see countries if they choose to enter a country and the
	 * arraylist doesn't already contain that country, use the append method to add
	 * that country
	 */

}
