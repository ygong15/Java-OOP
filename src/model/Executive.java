package model;

public class Executive extends Employee{
	
	public static final double EXECUTIVE_DISCOUNT = 0.70;
	

	public Executive(String email, String name, String passWord, String employeeID) {
		super(email, name, passWord, employeeID);
		super.discount = EXECUTIVE_DISCOUNT;
	}
	
	public void changePrice(Service serv, double newPrice) {
		serv.setPrice(newPrice);
	}
	
	public String toString() {
		return "Hi, " + this.name + ". Your company ID is: " + this.employeeID + ". Your current discount is: " + Math.round((1-this.discount) * 100) + "%";
	}
	
}
