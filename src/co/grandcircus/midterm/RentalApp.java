package co.grandcircus.midterm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RentalApp {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		String cont;
		boolean resume;

		System.out.println("Welcome to Away for the Day!\n");

		System.out.println(
				"We are a company based in Detroit that operates an online marketplace\nand hospitality service for people to rent short-term lodging");

		String humanName = Validator.getString(scnr,
				"I'd love to know who I'm working with. Please provide me with your full name (first and last) so I know with whom I'm working with:\n");

		System.out.println("\nThanks, " + humanName
				+ "!\nWe can now start the process of finding you a lovely place to rest your head for a few nights.\n");

		System.out.println("Go ahead and check out our lovely list of properies below\n");

		do {
			// System.out.println("Hello! Welcome to Away for the Day.");
			// System.out.println("1. View all properties\n");
			// System.out.println("2. Search properties by location\n");
			// System.out.println("3. Search properties by availability\n");
			// System.out.println("4. Search properties by rental type\n");
			// System.out.println("5. Exit the program\n");
			int userChoice = Validator.getInt(scnr, "How can we help you escape today?: ", 1, 5);

			if (userChoice == 1) {
				// need to create method for displaying properties
				viewProperties();
				// System.out.println("Would you like to choose another option");
			} else if (userChoice == 2) {
				// need to create method for searching by properties
				searchByLocation();
			} else if (userChoice == 3) {
				// need to create method for searching by properties
				searchByAvailability();
			} else {
				searchByPropertyType();
			}
			cont = Validator.getStringMatchingRegex(scnr, "Would you like to choose another option? (y/n): ",
					"Y|yes|N|no");
			if (cont.toLowerCase().startsWith("y")) {
				resume = true;
			} else {
				resume = false;
				break;
			}
		} while (resume);
	}

	public static void searchByPropertyType(Scanner scnr, ArrayList<Property> allRentals) {
		viewPropertyMenu();
		int propertyChoice = Validator.getInt(scnr, "What type of rental are you looking for?", 1, 4);

		String propertyType;
		Map<Integer, Property> byType = new HashMap<>();

		int count = 1;
		int propertyPick;

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
		default:
			propertyType = null;
		}
		for (Property property : allRentals) {
			if (property.getType().equals(propertyType)) {
				System.out.println(count + ". " + property.getName());
				byType.put(count, property);
				count++;
			}
		}

		
		//Used count - 1 because it will increment at the end of for loop, even when all items have been added already
		propertyPick = Validator.getInt(scnr, "Which " + propertyType + " would you like to view?", 1, count - 1);
		
		
		viewFullDetails(byType, propertyPick);
		
		
		
		
	}

	public static Property viewFullDetails(Map<Integer, Property> byType, int propertyPick) {
		Property theProperty = byType.get(propertyPick);
		
	}






	public static void viewPropertyMenu() {
		System.out.println("Great! Let's explore the variety of properties we offer: ");
		System.out.println("1. Luxurious Lofts");
		System.out.println("2. Chic Condo");
		System.out.println("3. Modern Single Family");
		System.out.println("4. Cozy Cottage");
	}
	private static void searchByAvailableDates() {
		// TODO Auto-generated method stub


	}

	public static void searchByLocation(Scanner scnr, ArrayList<Property> allLocations) {
		// Midtown
		// Downtown
		// West Village
		// "Corktown"
		//

		int propertyChoice = Validator.getInt(scnr, "Tell me where you'd like to go?", 1, 4);

		String propertyLocation;
		int count = 1;
		int propertyPick;

		switch (propertyChoice) {
		case 1:
			propertyLocation = "Midtown";
			break;
		case 2:
			propertyLocation = "Downtown";
			break;
		case 3:
			propertyLocation = "West Village";
			break;
		case 4:
			propertyLocation = "Corktown";
			break;
		default:
			propertyLocation = null;
		}
		for (Property property : allLocations) {
			if (property.getType().equals(propertyLocation)) {
				System.out.println(count + ". " + property.getName());
				count++;
			}
		}

		propertyPick = Validator.getInt(scnr, "Which property at " + propertyLocation + " would you like to view?", 1,
				count - 1);

		// viewFullDetails();

	}

	public static void viewProperties() {
		// TODO Auto-generated method stub

	}

	// reservation confirmation method:
	public void ReservationConfirmation(/* will it take an argument? */) {
		String renterName = "";
		String renterFirstName = "";
		String renterLastName = "";
		int rentalPrice = 0;
		String propertyName = "";
		String propertyLocation = "";
		int lengthOfStay = 0;
		LocalDate checkInDate;
		LocalDate checkOutDate;

		// Thank user for booking with us:
		System.out.println("Thanks for booking with us, " + renterName + "!\n");
		System.out.println("Here are the details of your researvation:\n");

		System.out.println("Name: " + renterFirstName + renterLastName + "\n");
		System.out.println("Price: " + "$" + rentalPrice + "\n");
		System.out.println("Property: " + propertyName + "\n");
		System.out.println("Location: " + propertyLocation + "\n");
		System.out.println("Duration of rental: " + lengthOfStay + "days.\n");
		System.out.println("Rental dates: " + checkInDate + checkOutDate + "\n");

	}
}