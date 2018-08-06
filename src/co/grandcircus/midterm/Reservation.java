package co.grandcircus.midterm;

import java.time.LocalDate;

public class Reservation {

	private String fullName;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private double price;
	private String propertyName;
	
	public Reservation(String fullName, LocalDate checkIn, LocalDate checkOut, double price, String propertyName) {
		this.fullName = fullName;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.price = price;
		this.propertyName = propertyName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String toString() {
		return fullName + "," + checkIn + "," + checkOut + "," + price + "," + propertyName;
	}
	
	
	
	
	
}
