package co.grandcircus.midterm;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RentalApp {

	// Eddy will fix up the wording and user experience
	// Add way to validate the dates they enter (in class tmrw)
	// shona add reserve class
	// eddy add input for what user would like to do at beginning
	// Change the wording of the viewMainMenu option "would you like to choose
	// another option" to be appropriate for terminating the program

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		final String Property_File = "properties.txt";

		ArrayList<Property> allRentals = PropertiesTextUtil.readFromFile(Property_File);

		System.out.println("Welcome to Away for the Day!\n");

		System.out.println(
				"We are a Startup based in Detroit that operates an online marketplace and hospitality service for people to rent short-term lodging.");

		String fullName = Validator.getStringMatchingRegex(scnr,
				"I'd love to know who I'm working with. Please provide me with your full name (first & last):\n",
				"^[a-z]+\\s[a-z]+$", false);

		String[] name = fullName.split(" ");

		String firstName = name[0];

		System.out.println("\n1. Book New a Reservation");
		System.out.println("2. View Existing Reservation");
		int firstChoice = Validator.getInt(scnr,
				"\nThanks, " + firstName + "! Please choose an option from the list above: \n", 1, 2);

		switch (firstChoice) {
		case 1:
			pickDates(scnr, allRentals, firstName, fullName);
			break;
		case 2:
			findReservation(scnr, allRentals, firstName, fullName);
			break;
		default:
			break;
		}

		scnr.close();
	}

	public static void pickDates(Scanner scnr, ArrayList<Property> allRentals, String firstName, String fullName) {
		LocalDate startDate;
		LocalDate endDate;

		startDate = Validator.getLocalDate(scnr,
				"\nLet me know when you are you looking to book your experience with us (MM/DD/YYYY): \n");

		int numDays = Validator.getInt(scnr, "\nHow many nights would you like to stay? \n");

		endDate = startDate.plusDays(numDays);

		System.out.println("\nOkay, we have your planned getaway set to start on "
				+ startDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + " and end on "
				+ endDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + "!");
		

		System.out.println("\nWe can't wait for you to see the amazing properites we have available for you.");

		// System.out.println("Now it's time to get this party started...\n");

		viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);

	}

	public static void viewMainMenu(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate,
			LocalDate endDate, String firstName, String fullName) {
		// String cont;
		// boolean resume;
		//
		// do {
		// resume = true;

		System.out.println("\n1. View Available Properties");
		System.out.println("2. View All Properties");
		System.out.println("3. Search Properties by Location");
		System.out.println("4. Search Properties by Rental Type");
		System.out.println("5. Depart\n");

		int userChoice = Validator.getInt(scnr, "Please choose an option from the list above: ", 1, 5);

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
		} else {
			System.out.println("\nSorry to see you go. Thanks for stopping by!");
			// resume = false;
			// break;
		}
		// cont = Validator.getStringMatchingRegex(scnr, "\nWould you like to choose
		// another option? (y/n): ",
		// "Y|yes|N|no", false);
		// if (cont.toLowerCase().startsWith("y")) {
		// resume = true;
		// } else {
		// resume = false;
		// }
		// } while (resume);
	}

	public static void viewProperties(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate,
			LocalDate endDate, boolean viewAll, String firstName, String fullName) {
		String reserve;
		int propertyPick;
		int count = 1;
		String startOver;
		Map<Integer, Property> byAvailability = new HashMap<>();

		System.out.println("\nHere are all of our available properties: \n");

		String format = "%-30s %-30s %-30s %-30s";
		System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
		System.out.println();
		for (Property property : allRentals) {
			if (startDate.isAfter(property.getDateAvailable()) || startDate.isEqual(property.getDateAvailable())) {
				System.out.printf(format, count + ". " + property.getName(), property.getLocation(),
						property.getPrice(), "Available Now");
				System.out.println();
				byAvailability.put(count, property);
				count++;
			}
		}
		System.out.println();

		if (viewAll) {
			ArrayList<Property> tempArray = new ArrayList<>();

			for (Property property : allRentals) {
				if (startDate.isBefore(property.getDateAvailable())) {
					tempArray.add(property);
				}
			}
			if (!tempArray.isEmpty()) {
				System.out.println("\nSorry, these properties are not available for your desired dates: ");
				for (Property property : allRentals) {
					if (startDate.isBefore(property.getDateAvailable())) {
						System.out.printf(format, property.getName(), property.getLocation(), "$" + property.getPrice(),
								"Available starting " + property.getDateAvailable().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
						System.out.println();
						
					}
				}
			}
		}

		reserve = Validator.getStringMatchingRegex(scnr, "Do any of the available destinations suit your needs? (y/n)",
				"y|yes|N|no", false);

		if (reserve.toLowerCase().startsWith("y")) {
			// Used count - 1 because it will increment at the end of for loop, even when
			// all items have been added already
			propertyPick = Validator.getInt(scnr,
					"\nWonderful! We are happy we could meet your needs today.\nWhich property would you like to reserve?",
					1, count - 1);
			reserveProperty(scnr, allRentals, byAvailability, propertyPick, startDate, endDate, firstName, fullName);
		}

		else {
			System.out.println("\nAww, we're sorry we were unable to meet your needs for these dates.");
			startOver = Validator.getStringMatchingRegex(scnr,
					"\nWould you like to explore our availability for different dates? (y/n)", "y|n|yes|no", false);

			if (startOver.toLowerCase().startsWith("y")) {
				pickDates(scnr, allRentals, firstName, fullName);
			} else {
				// Want program to terminate
				System.out.println("\nThanks for stopping by! Goodbye.");
			}
		}

	}

	public static void searchByPropertyType(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate,
			LocalDate endDate, String firstName, String fullName) {
		String propertyType;
		Map<Integer, Property> byType = new HashMap<>();

		int count;
		int propertyPick;
		String reserve;
		String resumeSearch;
		boolean searchHere;

		do {
			count = 1;
			searchHere = true;
			viewPropertyMenu();
			int propertyChoice = Validator.getInt(scnr, "\nWhat type of rental are you looking for?", 1, 5);

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

			if (!propertyType.matches("None")) {
				System.out.println("\nHere are all of our available " + propertyType + " properties: \n");

				String format = "%-30s %-30s %-30s %-30s";
				System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
				System.out.println();
				for (Property property : allRentals) {
					if (property.getType().matches(propertyType) && (startDate.isAfter(property.getDateAvailable())
							|| startDate.isEqual(property.getDateAvailable()))) {
						System.out.printf(format, count + ". " + property.getName(), property.getLocation(),
								"$" + property.getPrice(), "Available Now");
						System.out.println();
						byType.put(count, property);
						count++;
					}
				}

				System.out.println();

				ArrayList<Property> tempArray = new ArrayList<>();

				
				for (Property property : allRentals) {
					if (startDate.isBefore(property.getDateAvailable())) {
						tempArray.add(property);
					}
				}
				if (!tempArray.isEmpty()) {
					System.out.println("\nSorry, these " + propertyType
							+ " properties are not available for your desired dates: ");
					for (Property property : allRentals) {
						if (property.getType().matches(propertyType)
								&& startDate.isBefore(property.getDateAvailable())) {
							System.out.printf(format, property.getName(), property.getLocation(),
									"$" + property.getPrice(), "Available starting " + property.getDateAvailable().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
							System.out.println();
						}
					}            

				}

				reserve = Validator.getStringMatchingRegex(scnr,
						"\nDo any of these " + propertyType + " destinations suit your needs? (y/n)", "y|yes|N|no",
						false);

				if (reserve.toLowerCase().startsWith("y")) {
					// Used count - 1 because it will increment at the end of for loop, even when
					// all items have been added already
					System.out.println("Wonderful! We are happy we could meet your needs today.");
					propertyPick = Validator.getInt(scnr, "\nWhich " + propertyType + " would you like to reserve?", 1,
							count - 1);
					searchHere = false;
					reserveProperty(scnr, allRentals, byType, propertyPick, startDate, endDate, firstName, fullName);

				}

				else {
					System.out.println(
							"\nThat's okay! With our massive selection of properties, we're sure to find your perfect fit!");
					resumeSearch = Validator.getStringMatchingRegex(scnr,
							"\nWould you like to explore our other types of properties?(y/n)", "y|yes|N|no", false);

					if (resumeSearch.toLowerCase().startsWith("y")) {
						continue;
					} else {
						viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
						searchHere = false;
					}
				}
			}

			else {
				viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
				searchHere = false;

			}
		} while (searchHere);

	}

	public static void viewPropertyMenu() {
		System.out.println("\nGreat! Let's explore the variety of properties we offer: ");
		System.out.println("1. Luxurious Lofts");
		System.out.println("2. Chic Condo");
		System.out.println("3. Modern Single Family");
		System.out.println("4. Cozy Cottage");
		System.out.println("5. Return to main menu");
	}

	public static void searchByLocation(Scanner scnr, ArrayList<Property> allRentals, LocalDate startDate,
			LocalDate endDate, String firstName, String fullName) {
		String location;
		Map<Integer, Property> byLocation = new HashMap<>();

		int count;
		int propertyPick;
		String reserve;
		String resumeSearch;
		boolean searchHere;

		do {
			count = 1;
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

			if (!location.matches("None")) {
				System.out.println("\nHere are all of our available " + location + " properties: \n");

				String format = "%-30s %-30s %-30s %-30s";
				System.out.printf(format, "Property Name", "Location", "Price Per Night", "Availability");
				System.out.println();
				for (Property property : allRentals) {
					if (property.getLocation().matches(location) && (startDate.isAfter(property.getDateAvailable())
							|| startDate.isEqual(property.getDateAvailable()))) {
						System.out.printf(format, count + ". " + property.getName(), property.getLocation(),
								"$" + property.getPrice(), "Available Now");
						System.out.println();
						byLocation.put(count, property);
						count++;
					}
				}

				System.out.println();

				ArrayList<Property> tempArray = new ArrayList<>();

				for (Property property : allRentals) {
					if (startDate.isBefore(property.getDateAvailable())) {
						tempArray.add(property);
					}
				}

				if (!tempArray.isEmpty()) {
					System.out.println(
							"\nSorry, these " + location + " properties are not available for your desired dates: ");
					for (Property property : allRentals) {
						if (property.getLocation().matches(location) && startDate.isBefore(property.getDateAvailable())) {
							System.out.printf(format, property.getName(), property.getLocation(),
									"$" + property.getPrice(), "Available starting " + property.getDateAvailable().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
							System.out.println();
						}
					}
				}

				reserve = Validator.getStringMatchingRegex(scnr,
						"\nDo any of these " + location + " destinations suit your needs? (y/n)", "y|yes|N|no", false);

				if (reserve.toLowerCase().startsWith("y")) {
					// Used count - 1 because it will increment at the end of for loop, even when
					// all items have been added already
					propertyPick = Validator.getInt(scnr,
							"\nWonderful! We are happy we could meet your needs today.\nWhich " + location
									+ " property would you like to reserve?",
							1, count - 1);
					reserveProperty(scnr, allRentals, byLocation, propertyPick, startDate, endDate, firstName,
							fullName);
					searchHere = false;
				}

				else {
					System.out.println(
							"\nThat's okay! With our massive selection of properties, we're sure to find your perfect fit!");
					resumeSearch = Validator.getStringMatchingRegex(scnr,
							"\nWould you like to explore properties at our other locations?(y/n)", "y|yes|N|no", false);

					if (resumeSearch.toLowerCase().startsWith("y")) {
						continue;
					} else {
						searchHere = false;
						viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);

					}
				}
			}

			else {
				viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
				searchHere = false;

			}
		} while (searchHere);

	}

	public static void viewLocationMenu() {
		System.out.println("Wonderful! Let's explore the variety of locations we offer: ");
		System.out.println("1. Downtown");
		System.out.println("2. Midtown");
		System.out.println("3. West Village");
		System.out.println("4. Corktown");
		System.out.println("5. Return to main menu");
	}

	public static void reserveProperty(Scanner scnr, ArrayList<Property> allRentals,
			Map<Integer, Property> finalOptions, int propertyPick, LocalDate startDate, LocalDate endDate,
			String firstName, String fullName) {
		String confirmation;
		double fullPrice;
		int numDays;
		Period timeBetween = Period.between(startDate, endDate);

		Property theProperty = finalOptions.get(propertyPick);

		numDays = timeBetween.getDays();

		fullPrice = numDays * theProperty.getPrice();

		System.out.println(
				firstName + ", you have selected " + theProperty.getName() + ". Here are the full details: \n");
		System.out.println();

		System.out.println(theProperty.getFullDetails());

		System.out.printf("%-40s %-40s\n", "Check-in Date: " + startDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")), "Check-out Date: " + endDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));

		System.out.println("The price summary is as follows: ");

		// Not working

		System.out.printf("%-80s\n %-80s\n %-80s\n", "Price per night: " + theProperty.getPrice(),
				"Number of nights: " + numDays, "Total price: " + fullPrice);

		confirmation = Validator.getStringMatchingRegex(scnr, "Would you like to confirm this reservation? (y/n)",
				"y|yes|no|n", false);

		if (confirmation.toLowerCase().startsWith("y")) {
			reservationConfirmation(allRentals, theProperty, startDate, endDate, numDays, fullPrice, firstName,
					fullName);
		} else {
			System.out.println("That's okay! Let's go back to the menu and explore our other options!");
			viewMainMenu(scnr, allRentals, startDate, endDate, firstName, fullName);
		}
	}

	// reservation confirmation method:
	public static void reservationConfirmation(ArrayList<Property> allRentals, Property property, LocalDate startDate,
			LocalDate endDate, int numDays, double fullPrice, String firstName, String fullName) {

		// Thank user for booking with us:
		System.out.println("Thanks for booking with us, " + firstName + "!\nYour reservation has been confirmed.");
		System.out.println("Here are the details of your researvation for your records:\n");

		System.out.println("Name: " + fullName + "\n");
		System.out.println("Total Price: " + "$" + fullPrice + "\n");
		System.out.println("Property: " + property.getName() + "\n");
		System.out.println("Location: " + property.getLocation() + "\n");
		System.out.println("Duration of rental: " + numDays + "days.\n");
		System.out.println("Rental dates: " + startDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + " - " + endDate.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + "\n");

		// created instance and plugged in user's info (instance variables) into
		// constructor
		Reservation reservation = new Reservation(fullName, startDate, endDate, fullPrice, property.getName());
		// added reservation to the file. This allows us to save user data
		ReservationsTextUtil.appendToFile(reservation, "reservations.txt");

		// Updates date available on file
		property.setDateAvailable(endDate);
		PropertiesTextUtil.writeToFile(allRentals, "properties.txt");
	}

	public static void findReservation(Scanner scnr, ArrayList<Property> allRentals, String firstName,
			String fullName) {
		Reservation userReservation = null;
		String newReservation;
		int userChoice;

		ArrayList<Reservation> allReservations = ReservationsTextUtil.readFromFile("reservations.txt");
		// if name matches, we can let part know that we've found their reservation
		for (Reservation reservation : allReservations) {
			if (reservation.getFullName().matches(fullName)) {
				System.out.println("Okay, we've found your reservation.");
				userReservation = reservation;
				break;
			}
		}
		if (userReservation == null) {
			System.out.println("\nSorry, we couldn't find a reservation with that name.");
			newReservation = Validator.getStringMatchingRegex(scnr, "Would you like to book a new reservation? (y/n)",
					"y|n|no|yes", false);
			if (newReservation.toLowerCase().startsWith("y")) {
				pickDates(scnr, allRentals, firstName, fullName);
			}
		} else {
			userChoice = Validator.getInt(scnr, firstName + ", what would you like to do with this reservation?", 1, 4);
			viewReservationMenu();

			switch (userChoice) {
			case 1:
				System.out.println("Here are your reservation details: ");
				System.out.println("Name: " + userReservation.getFullName());
				System.out.println("Check-In: " + userReservation.getCheckIn().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
				System.out.println("Check-Out: " + userReservation.getCheckOut().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")));
				System.out.println("Total Price: " + userReservation.getPrice());
				System.out.println("Property: " + userReservation.getPropertyName());
				break;
			case 2:
				checkOut(allRentals, userReservation, allReservations);
				break;
			case 3:
				break;
			default:
				break;

			}
		}

	}

	private static void viewReservationMenu() {
		System.out.println("1. View reservation details");
		System.out.println("2. Checkout of rental");
		System.out.println("3. Exit");

	}

	public static void checkOut(ArrayList<Property> allRentals, Reservation reservation,
			ArrayList<Reservation> allReservations) {
		for (Property property : allRentals) {
			if (property.getName().matches(reservation.getPropertyName())) {
				property.setDateAvailable(LocalDate.now());
			}
		}
		System.out.println("Thank you for booking your experience with us! Your checkOut has been confimed. Goodbye!");

		PropertiesTextUtil.writeToFile(allRentals, "properties.txt");

		allReservations.remove(reservation);
		ReservationsTextUtil.writeToFile(allReservations, "reservations.txt");

	}

}
