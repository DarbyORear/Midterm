package co.grandcircus.midterm;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RentalApp {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		final String Property_File = "properties.txt";
		
		ArrayList<Property> allRentals = PropertiesTextUtil.readFromFile(Property_File);

		System.out.println("Welcome to Away for the Day!\n");

		System.out.println(
				"We are a Startup based in Detroit that operates an online marketplace and hospitality service for people to rent short-term lodging");

		String fullName = Validator.getStringMatchingRegex(scnr,
				"I'd love to know who I'm working with. Please provide me with your full name (first & last):\n",
				"^[a-z]+\\s[a-z]+$", false);

		String[] name = fullName.split(" ");

		String firstName = name[0];
		
		System.out.println("\nThanks, " + firstName
				+ "!\nWe are starting the process of finding you a lovely place to rest your head for a few nights.\n");

		pickDates(scnr, allRentals, firstName, fullName);
		
		
		scnr.close();
	}
	
	
	public static void pickDates(Scanner scnr, ArrayList<Property> allRentals, String firstName, String fullName) {
		LocalDate startDate;
		LocalDate endDate;
		
		startDate = Validator.getLocalDate(scnr, "Now let me know when you are you looking to book your experience with us (MM/DD/YYYY): \n");

		int numDays = Validator.getInt(scnr, "How many days would you like to stay? \n");

		endDate = startDate.plusDays(numDays);

		System.out.println("Your trip will start " + startDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + " and end " + endDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));

		System.out.println("Awesome! Now it's time to get this party started...\n");
			
		viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
			
	}
	
	
	public static void viewMainMenu(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate, LocalDate endDate, String firstName, String fullName) {
		String cont;
		boolean resume;
		
		do {
			resume = true;
			
			System.out.println("1. View Available Properties");
			System.out.println("2. View All Properties\n");
			System.out.println("3. Search Properties by Location\n");
			System.out.println("4. Search Properties by Rental Type\n");
			System.out.println("5. Depart\n");

			int userChoice = Validator.getInt(scnr, "\nHow can we help you escape today?: ", 1, 5);

			if (userChoice == 1) {
				// need to create method for displaying properties
				viewProperties(scnr, allRentals, startDate, endDate, false, firstName, fullName);
				// System.out.println("Would you like to choose another option");
			} else if (userChoice == 2) {
				viewProperties(scnr, allRentals, startDate, endDate, true, firstName, fullName);
			} else if (userChoice == 3) {
				// need to create method for searching by properties
				searchByLocation(scnr, allRentals, startDate, endDate, firstName, fullName);
			} else if (userChoice == 4) {
				searchByPropertyType(scnr, allRentals, startDate, endDate, firstName, fullName);
			}
			else {
				System.out.println("Sorry to see you go. Thanks for stopping by!");
				resume = false;
				break;
			}
			cont = Validator.getStringMatchingRegex(scnr, "Would you like to choose another option? (y/n): ",
					"Y|yes|N|no", false);
			if (cont.toLowerCase().startsWith("y")) {
				resume = true;
			} else {
				resume = false;
			}
		}
		while (resume);
	}
	
	
	public static void viewProperties(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate, LocalDate endDate, boolean viewAll, String firstName, String fullName) {
		String reserve;
		int propertyPick;
		int count = 1;
		String startOver;
		Map<Integer, Property> byAvailability = new HashMap<>();
		
		System.out.println("Here are all of our available properties: \n");
		
		String format = "%-30s %-30s %-30s %-30s";
		System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
		System.out.println();
		for (Property property : allRentals) {
			if (startDate.isAfter(property.getDateAvailable()) || startDate.isEqual(property.getDateAvailable())) {
				System.out.printf(format, count + ". " + property.getName(), property.getLocation(), property.getPrice(), "Available Now");
				System.out.println();
				byAvailability.put(count, property);
				count++;
			}
		}
		System.out.println();
		
		
		if (viewAll) {
			System.out.println("Sorry, these properties are not available for your desired dates: ");
			for (Property property : allRentals) {
				if (startDate.isBefore(property.getDateAvailable())) {
					System.out.printf(format, property.getName(), property.getLocation(), "$" + property.getPrice(), "Available starting " + property.getDateAvailable());
					System.out.println();
				}
			}
		}
		
		reserve = Validator.getStringMatchingRegex(scnr, "Do any of the available destinations suit your needs? (y/n)", 
				"y|yes|N|no", false);
		
		if (reserve.toLowerCase().startsWith("y")) {
			// Used count - 1 because it will increment at the end of for loop, even when
			// all items have been added already
			propertyPick = Validator.getInt(scnr, "Wonderful! We are happy we could meet your needs today.\nWhich property would you like to reserve?", 1, count - 1);
			reserveProperty(scnr, allRentals, byAvailability, propertyPick,startDate, endDate, firstName, fullName);
		}
		
		else {
			System.out.println("Aww, we're sorry we were unable to meet your needs for these dates.");
			startOver = Validator.getStringMatchingRegex(scnr, "Would you like to explore our availability for different dates? (y/n)", "y|n|yes|no", false);
			if (startOver.toLowerCase().startsWith("y")) {
				pickDates(scnr, allRentals, firstName, fullName);
			}
		}
		
	}

	
	public static void searchByPropertyType(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate, LocalDate endDate, String firstName, String fullName) {
		String propertyType;
		Map<Integer, Property> byType = new HashMap<>();

		int count = 1;
		int propertyPick;
		String reserve;
		String resumeSearch;
		boolean searchHere;
		
		do {
			searchHere = true;
			viewPropertyMenu();
			int propertyChoice = Validator.getInt(scnr, "What type of rental are you looking for?", 1, 5);
	
			switch (propertyChoice) {
			case 1:
				propertyType = "Loft";
				break;
			case 2:
				propertyType = "Condo";
				break;
			case 3:
				propertyType = "Single Family";
				break;
			case 4:
				propertyType = "Cottage";
				break;
			case 5:
				propertyType = "None";
				break;
			default:
				propertyType = null;
			}
			
			if (!propertyType.equals("None")) {
				System.out.println("Here are all of our available " + propertyType + " properties: \n");
				
				String format = "%-30s %-30s %-30s %-30s";
				System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
				for (Property property : allRentals) {
					if (property.getType().equals(propertyType) 
							&& (startDate.isAfter(property.getDateAvailable()) || startDate.isEqual(property.getDateAvailable()))) {
						System.out.printf(format, count + ". " + property.getName(), property.getLocation(), "$" + property.getPrice(), "Available Now");
						System.out.println();
						byType.put(count, property);
						count++;
					}
				}
				
				System.out.println();
				System.out.println("Sorry, these " + propertyType + " properties are not available for your desired dates: ");
				for (Property property : allRentals) {
					if (property.getType().equals(propertyType) 
							&& startDate.isBefore(property.getDateAvailable())) {
						System.out.printf(format, property.getName(), property.getLocation(), "$" + property.getPrice(), "Available starting " + property.getDateAvailable());
						System.out.println();
					}
				}
				
				reserve = Validator.getStringMatchingRegex(scnr, "Do any of these " + propertyType + " destinations suit your needs? (y/n)", 
						"y|yes|N|no", false);
				
				if (reserve.toLowerCase().startsWith("y")) {
					// Used count - 1 because it will increment at the end of for loop, even when
					// all items have been added already
					propertyPick = Validator.getInt(scnr, "Wonderful! We are happy we could meet your needs today.\nWhich " + propertyType + " would you like to reserve?", 1, count - 1);
					reserveProperty(scnr, allRentals, byType, propertyPick, startDate, endDate, firstName, fullName);
				}
				
				else {
					System.out.println("That's okay! With our massive selection of properties, we're sure to find your perfect fit!");
					resumeSearch = Validator.getStringMatchingRegex(scnr, "Would you like to explore our other types of properties?(y/n)", "y|yes|N|no", false);
					
					if (resumeSearch.toLowerCase().startsWith("y")) {
						continue;
					}
					else {
						viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
						searchHere = false;
					}
				}
			}
			
			else {
				viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
				searchHere = false;
				
			}
		}
		while (searchHere);

	}


	public static void viewPropertyMenu() {
		System.out.println("Great! Let's explore the variety of properties we offer: ");
		System.out.println("1. Luxurious Lofts");
		System.out.println("2. Chic Condo");
		System.out.println("3. Modern Single Family");
		System.out.println("4. Cozy Cottage");
		System.out.println("5. Return to main menu");
	}

	
	public static void searchByLocation(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate, LocalDate endDate, String firstName, String fullName) {
		String location;
		Map<Integer, Property> byLocation = new HashMap<>();

		int count = 1;
		int propertyPick;
		String reserve;
		String resumeSearch;
		boolean searchHere;
		
		do {
			searchHere = true;
			viewLocationMenu();
			int propertyChoice = Validator.getInt(scnr, "Where would you like to escape?", 1, 5);
	
			switch (propertyChoice) {
			case 1:
				location = "Downtown";
				break;
			case 2:
				location = "Midtown";
				break;
			case 3:
				location = "West Village";
				break;
			case 4:
				location = "Corktown";
				break;
			case 5:
				location = "None";
				break;
			default:
				location = null;
			}
			
			if (!location.equals("None")) {
				System.out.println("Here are all of our available " + location + " properties: \n");
				
				String format = "%-30s %-30s %-30s %-30s";
				System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
				System.out.println();
				for (Property property : allRentals) {
					if (property.getLocation().matches(location) 
							&& (startDate.isAfter(property.getDateAvailable()) || startDate.isEqual(property.getDateAvailable()))) {
						System.out.printf(format, count + ". " + property.getName(), property.getLocation(), "$" + property.getPrice(), "Available Now");
						System.out.println();
						byLocation.put(count, property);
						count++;
					}
				}
				
				//FIX-IT: Change .equals to .matches. 
				//Add a line between headers and list
				//add a temp array to store the unavailable properties and only print the details below if its not empty
				//Adding option to verify the dates selected aree the actual dates they want
				
				System.out.println();
				System.out.println("Sorry, these " + location + " properties are not available for your desired dates: ");
				for (Property property : allRentals) {
					if (property.getType().matches(location) 
							&& startDate.isBefore(property.getDateAvailable())) {
						System.out.printf(format, property.getName(), property.getLocation(), "$" + property.getPrice(), "Available starting " + property.getDateAvailable());
						System.out.println();
					}
				}
				
				reserve = Validator.getStringMatchingRegex(scnr, "Do any of these " + location + " destinations suit your needs? (y/n)", 
						"y|yes|N|no", false);
				
				if (reserve.toLowerCase().startsWith("y")) {
					// Used count - 1 because it will increment at the end of for loop, even when
					// all items have been added already
					propertyPick = Validator.getInt(scnr, "Wonderful! We are happy we could meet your needs today.\nWhich " + location + " property would you like to reserve?", 1, count - 1);
					reserveProperty(scnr, allRentals, byLocation, propertyPick,startDate, endDate, firstName, fullName);
				}
				
				else {
					System.out.println("That's okay! With our massive selection of properties, we're sure to find your perfect fit!");
					resumeSearch = Validator.getStringMatchingRegex(scnr, "Would you like to explore properties at our other locations?(y/n)", "y|yes|N|no", false);
					
					if (resumeSearch.toLowerCase().startsWith("y")) {
						continue;
					}
					else {
						viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
						searchHere = false;
					}
				}
			}
			
			else {
				viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
				searchHere = false;
				
			}
		}
		while (searchHere);

	}

	
	public static void viewLocationMenu() {
		System.out.println("Wonderful! Let's explore the variety of locations we offer: ");
		System.out.println("1. Downtown");
		System.out.println("2. Midtown");
		System.out.println("3. West Village");
		System.out.println("4. Corktown");
		System.out.println("5. Return to main menu");
	}
	
	
	
	public static void reserveProperty(Scanner scnr, ArrayList<Property> allRentals, Map<Integer, Property> finalOptions, int propertyPick, LocalDate startDate, LocalDate endDate, String firstName, String fullName) {
		String confirmation;
		double fullPrice;
		int numDays;
		Period timeBetween = Period.between(startDate, endDate);
		
		Property theProperty = finalOptions.get(propertyPick);
		
		numDays = timeBetween.getDays();
		
		fullPrice = numDays * theProperty.getPrice();

		System.out.println(firstName + ", you have selected " + theProperty.getName() + ". Here are the full details: \n");
		System.out.println();
		
		System.out.println(theProperty.getFullDetails());
		
		System.out.printf("%-40s %-40s\n", "Check-in Date: " + startDate, "Check-out Date: " + endDate);
		
		System.out.println("The price summary is as follows: ");
		
		String formattedPrice = String.format("%.2f", "" + fullPrice);
		
		System.out.printf("%-80s\n %-80s\n %-80s\n", "Price per night: " + theProperty.getPrice(), 
				"Number of nights: " + numDays, "Total price: " + formattedPrice);
		
		confirmation = Validator.getStringMatchingRegex(scnr, "Would you like to confirm this reservation? (y/n)", "y|yes|no|n", false);
		
		if (confirmation.toLowerCase().startsWith("y")) {
			reservationConfirmation(theProperty, startDate, endDate, numDays, formattedPrice, firstName, fullName);
		}
		else {
			System.out.println("That's okay! Let's go back to the menu and explore our other options!");
			viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
		}
	}
	
	

	// reservation confirmation method:
	public static void reservationConfirmation(Property property, LocalDate startDate, LocalDate endDate, int numDays, String price, String firstName, String fullName) {
		
		// Thank user for booking with us:
		System.out.println("Thanks for booking with us, " + firstName + "!\nYour reservation has been confirmed.");
		System.out.println("Here are the details of your researvation for your records:\n");

		System.out.println("Name: " + fullName + "\n");
		System.out.println("Total Price: " + "$" + price + "\n");
		System.out.println("Property: " + property.getName() + "\n");
		System.out.println("Location: " + property.getLocation() + "\n");
		System.out.println("Duration of rental: " + numDays + "days.\n");
		System.out.println("Rental dates: " + startDate + " - " + endDate + "\n");

	}
	
}
