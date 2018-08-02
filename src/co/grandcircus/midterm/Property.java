package co.grandcircus.midterm;

public class Property {
	private String name;
	private String type;
	private String location;
	private String amenities;
	private double price;
	private boolean available;
	
	public Property(String name, String type, String location, String amenities, double price, boolean available) {
		this.name = name;
		this.type = type;
		this.location = location;
		this.amenities = amenities;
		this.price = price;
		this.available = available;
	}
	
	public Property() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return name + "," + location + "," + type + "," + amenities + "," + price + "," + available;
	}
	
	
	

}