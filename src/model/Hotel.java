package model;

import java.time.LocalDate;

public class Hotel extends Service {
	protected String location;
	protected String hotelName;
	protected RoomType roomType;
	protected int duration;

	public Hotel(double price, String startTime, String endTime, String location, String hotelName, RoomType roomType, int duration) {
		super(price, startTime, endTime);
		// TODO Auto-generated constructor stub
		this.location = location;
		this.hotelName = hotelName;
		this.roomType = roomType;
		this.duration = duration;
				
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public int getDuration() {
		return duration;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return "Hotel Location: " + this.location + " Hotel Name: " + this.hotelName;
	}

}
