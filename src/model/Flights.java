package model;

import java.time.LocalDate;
import java.util.Map;

public class Flights extends Transportation{
	protected String airLine;
	protected String flightNumber;

	public Flights(double price, String startTime, String endTime, String departureLocation,
			String arrivalLocation, Map<ClassType, Integer> availableSeat, String airLine, String flightNumber) {
		super(price, startTime, endTime, departureLocation, arrivalLocation, availableSeat);
		this.airLine = airLine;
		this.flightNumber = flightNumber;
	}

	public String getAirLine() {
		return airLine;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public String toString() {
		return "Departure Time: " + this.startTime  + " Arrival Time: " + this.endTime + " Departure Location: " + this.departureLocation + " Arrival Location: " + this.arrivalLocation
				+ " Airline Company: " + this.airLine + " Flight Number: " + this.flightNumber;
	}
	
	

}
