package model;
import java.time.LocalDate;


public abstract class Service {
	
	protected double price;
	protected String startTime;
	protected String endTime;
	
	public Service(double price, String startTime, String endTime) {
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public double getPrice() {
		return price;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}
