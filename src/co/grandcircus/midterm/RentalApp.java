package co.grandcircus.midterm;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RentalApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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