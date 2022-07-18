package model;

import java.time.LocalDate;
import java.util.Map;

public abstract class Transportation extends Service{
	
	protected String departureLocation;
	protected String arrivalLocation;
	protected Map<ClassType, Integer>availableSeat;
	protected ClassType seatType;

	public Transportation(double price, String startTime, String endTime, String departureLocation,
							String arrivalLocation, Map<ClassType, Integer> availableSeat) {
		super(price, startTime, endTime);
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
		this.availableSeat = availableSeat;
		//by default, class type will be economy which matches the price in the tsv. User will be able to change this later
		this.seatType = ClassType.ECONOMY;
	}

	public ClassType getSeatType() {
		return seatType;
	}

	public void setSeatType(ClassType seatType) {
		this.seatType = seatType;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}


	public Map<ClassType, Integer> getAvailableSeat() {
		return availableSeat;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}


	public void setAvailableSeat(Map<ClassType, Integer> availableSeat) {
		this.availableSeat = availableSeat;
	}
	
	
}
