package co.grandcircus.midterm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class RentalApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Hello! Welcome to Away for the Day. How can we help you escape today?: ");
		System.out.println("1. View all properties\n");
		System.out.println("2. Search properties by location\n");
		System.out.println("3. Search properties by availability\n");
		System.out.println("4. Search properties by rental type\n\n");
		int userChoice = scnr.nextInt();
		
		if(userChoice == 1) {
			//need to create method for displaying properties
			viewProperties();
		} else if(userChoice == 2) {
			//need to create method for searching by properties
			searchByLocation();
		} else if(userChoice == 3) {
			//need to create method for searching by properties
			searchByAvailability();
		} else {
			searchByPropertyType();
		}
	
	}






//reservation confirmation method:
public void ReservationConfirmation(/*will it take an argument?*/) {
	String renterName="";
	String renterFirstName="";
	String renterLastName="";
	int rentalPrice=0;
	String propertyName= "";
	String propertyLocation = "";
	int lengthOfStay = 0;
	LocalDate checkInDate;
	LocalDate checkOutDate;
	
	//Thank user for booking with us:
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