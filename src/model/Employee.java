package model;

import java.util.ArrayList;
import java.util.Map;

public class Employee extends User {
	protected String employeeID;
	protected double discount;
	
	public static final double EMPLOYEE_DISCOUNT = 0.85;

	public Employee(String email, String name, String passWord, String employeeID) {
		super(email, name, passWord);
		this.employeeID = employeeID;
		this.discount = EMPLOYEE_DISCOUNT;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public double getDiscount() {
		return discount;
	}

	
	public void addService(Service serv, Map<String, ArrayList<Service>> dataBase) {
		String servType = serv.getClass().getSimpleName();
		for(String t: dataBase.keySet()) {
			if(servType.equalsIgnoreCase(t)) {
				ArrayList<Service> value = dataBase.get(t);
				value.add(serv);
			}
		}
	}
	
	public String toString() {
		return "Hi, " + this.name + ". Your Company ID is: " + this.employeeID + ". Your Discount is: " + Math.round((1-this.discount) * 100) + "%"; 
	}

}
