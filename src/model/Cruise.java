package model;

import java.time.LocalDate;
import java.util.Map;

public class Cruise extends Transportation{
	protected String cruiseCompany;
	protected String cruiseNumber;

	public Cruise(double price, String startTime, String endTime, String departureLocation,
			String arrivalLocation, Map<ClassType, Integer> availableSeat, String cruiseCompany, String cruiseNumber) {
		super(price, startTime, endTime, departureLocation, arrivalLocation, availableSeat);
		this.cruiseCompany = cruiseCompany;
		this.cruiseNumber = cruiseNumber;
	}

	public String getCruiseCompany() {
		return cruiseCompany;
	}

	public String getCruiseNumber() {
		return cruiseNumber;
	}

	public void setCruiseCompany(String cruiseCompany) {
		this.cruiseCompany = cruiseCompany;
	}

	public void setCruiseNumber(String cruiseNumber) {
		this.cruiseNumber = cruiseNumber;
	}
	
	public String toString() {
		return "Departure Time: " + this.startTime + " Arrival Time: " + this.endTime + " Departure Location: " + this.departureLocation + " Arrival Location: " + this.arrivalLocation
				+ " Cruise Company: " + this.cruiseCompany + " Cruise Number: " + this.cruiseNumber;
	}

}
