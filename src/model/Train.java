package model;

import java.time.LocalDate;
import java.util.Map;

public class Train extends Transportation{
	protected String trainCompany;
	protected String trainNumber;

	public Train(double price, String startTime, String endTime, String departureLocation, String arrivalLocation,
			Map<ClassType, Integer> availableSeat, String trainCompany, String trainNumber) {
		super(price, startTime, endTime, departureLocation, arrivalLocation, availableSeat);
		// TODO Auto-generated constructor stub
		this.trainCompany = trainCompany;
		this.trainNumber = trainNumber;
	}

	public String getTrainCompany() {
		return trainCompany;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainCompany(String trainCompany) {
		this.trainCompany = trainCompany;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	
	public String toString() {
		return "Departure Time: " + this.startTime + " Arrival Time: " + this.endTime + " Departure Location: " + this.departureLocation + " Arrival Location: " + this.arrivalLocation
				+ " Train Company: " + this.trainCompany + " Cruise Number: " + this.trainNumber;
	}
	

}
