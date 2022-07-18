package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.*;


public class UserInter {
	//map -> service data base
	//set -> user data base
	protected Map<String, ArrayList<Service>> serviceData = new HashMap<>();
	protected Set<User> userData = new HashSet<>();
	//current user helps to locate the user after logged in
	protected User currentUser = null;
	//a shopping cart for adding in services
	protected ArrayList<Service> cart;
	
	//helper object
	protected Helper bff; 
	
	//default two class level variables, will be used later
	private boolean exit = false;
	private double totalPayment  = 0;
	
	//Constructor
	public UserInter() {
		this.serviceData = FileReader.readService();
		this.userData = FileReader.readUser();
		this.bff = new Helper();
		this.cart = new ArrayList<>();
	}
	
	//method in charge of running the program -> invoke helper methods
	public void run() {
		//exit indicator -> default to false
		//start the program by letting user login or signup
		logIn();
		while(this.currentUser!= null && !exit) {
			//different menus will be shown for three different types of users
			if(this.currentUser.getClass().getSimpleName().equalsIgnoreCase("Customer") ) {
				int userInput = shoppingMenu();
				addServiceToCart(userInput);
			}else if(this.currentUser.getClass().getSimpleName().equalsIgnoreCase("Employee")) {
				String option = bff.inputWord("Do you want to add service or shop(please enter 'add' or 'shop')", "add", "shop");
				if(option.equalsIgnoreCase("shop")) {
					int userInput = shoppingMenu();
					addServiceToCart(userInput);
				}else {
					addServiceToDataBase();
				}
				
			}else {
				String option = bff.inputWord("Do you want to add service, change price, or shop(please enter 'add', 'change', or 'shop')", "add", "shop", "change");
				if(option.equalsIgnoreCase("shop")) {
					int userInput = shoppingMenu();
					addServiceToCart(userInput);
				}else if (option.equalsIgnoreCase("change")) {
					changePrice();
				}else {
					addServiceToDataBase();
				}
			}
		}
		System.out.println("\nThanks for using Expedia, bye!");
	}
	
	//login method: allow user to either login or sign up, user has three chances to enter the correct password
	private void logIn() {
		//let user choose whether they want to login or signup
		System.out.println("Hi, Welcome to Expedia! Please Login or Signup for you account.");
		String result = bff.inputWord("Type here (login or signup) to access services", "login", "signup");
	
		if(result.equalsIgnoreCase("login")) {
			//store user's entered email for data base checking
			String email = bff.inputWord("Please enter your account email");
			User temp = null;
			//check whether user's email is in the database, if so temp = the user
			for(User u: userData) {
				if(u.getEmail().equalsIgnoreCase(email)) {
					temp = u;
				}
			}
			//if temp == null, user email is not in the database -> make user signup
			if(temp == null) {
				System.out.println("\nSorry, the email you entered is not in our database. Please signup for an account.");
				String newName = bff.inputWord("Please enter your name");
				String newEmail = bff.inputWord("Please set your email");
				String newPass = bff.inputWord("Please set your password");
				User newUser = new Customer(newEmail, newName, newPass, 0);
				//add the new user to database
				userData.add(newUser);
				//set current user = new user
				this.currentUser = newUser;
				return;
			}
			//user has 3 chances to enter the correct password
			int i = 0;
			while(i<3) {
				String pass = bff.inputWord("Please enter your password");
				i++;
				//if user enters the correct password -> current user = temp
				if(temp.getPassWord().equals(pass)) {
					this.currentUser = temp;
					return;
				}
				System.out.println("Sorry, the password you entered is incorrect. You have " + (3-i) + " chances remaining");
			}
			return;
		}else {
			//exactly the same to the signup process described above
			String newName = bff.inputWord("Please enter your name");
			String newEmail = bff.inputWord("Please set your email");
			String newPass = bff.inputWord("Please set your password");
			User newUser = new Customer(newEmail, newName, newPass, 0);
			userData.add(newUser);
			this.currentUser = newUser;
			return;
			
		}
		
	}
	
	//shoppingMenu: print the main menu for shopping
	private int shoppingMenu() {
			System.out.println("-".repeat(200));
			System.out.println("Please select from below (please enter an integer): ");
			System.out.println("\t0) Book Flight\n\t1) Book Cruise\n\t2) Book Train\n\t3) Book Hotel\n\t4) Check Shopping Cart\n\t5) CheckOut\n\t6) Change Password ");
			int result = bff.inputInt("Please choose an option from above (enter -1 to exit)",-1,6);
			return result;
	}
	
	//addService: adding services into the shopping cart
	private void addServiceToCart(int choice) {
		switch(choice) {
		case -1:	//user will quit the entire program after entering -1, user will have a chance to return to main manu later
			this.exit = true;
			break;
		//shop flights
		case 0:
			shopTransportation("Flights");
			break;
		//shop cruise
		case 1:
			shopTransportation("Cruise");
			break;
		//shop train
		case 2:
			shopTransportation("Train");
			break;
		//shop hotel
		case 3:
			shopHotel();
			break;
		//check current shopping cart
		case 4:
			checkCart();
			break;
		//check out shoppign cart
		case 5:
			checkOut();
			break;
		//change password
		case 6:
			System.out.println("Your old password is: " + this.currentUser.getPassWord());
			String newPass = bff.inputWord("Please enter your new password");
			this.currentUser.setPassWord(newPass);
			System.out.println("\nYour password has been successfully updated to " + newPass + "\n");
			break;
		//in version 2 -> include a delete service from shopping cart method & allow users to access more detailed account info
		default:
			System.out.println("Not implemented yet");
		}
	}
	
	//helper method for shopping hotels
	private void shopHotel() {
		//create a reference to all hotels
		ArrayList<Service> hotelList = serviceData.get("Hotel");
		System.out.println("-".repeat(200));
		//print all hotels
		System.out.println("Below are hotels you can choose: ");
		for(int i = 0; i<hotelList.size(); i++) {
			System.out.println("\t" + i + ") " + hotelList.get(i));
			System.out.println();
		}
		//allow user to choose a hotel by entering int
		int userInput = bff.inputInt("Please enter an integer to select a hotel(enter -1 to return to menu)");
		if(userInput == -1) {
			return;
		}
		Hotel temp = (Hotel) hotelList.get(userInput);
		//get how many nights the user will be staying in
		int duration = bff.inputInt("\nPlease enter how many dates you will be staying", 1, 100);
		//calculate prices for different types of rooms based on enum value
		double standardPrice = temp.getPrice() * RoomType.STANDARD.rate() * duration;
		double deluxPrice = temp.getPrice() * RoomType.DELUX.rate() * duration;
		double suitePrice = temp.getPrice() * RoomType.SUITE.rate() * duration;
		
		System.out.println();
		
		//print room price (based on types) for users and let user chooses
		System.out.println(temp);
		System.out.println("\tStandard Price: $" + standardPrice + "\tDelux Room Price: $" + deluxPrice + "\tSuite Price: $" + suitePrice);
		String userChoice = bff.inputWord("Please type in a room type to book (enter 'none' to return to menu)", "standard", "suite", "delux", "none" );
		
		//changing related fields of temp and add temp to cart
		if(userChoice.equalsIgnoreCase("standard")){
			temp.setPrice(standardPrice);
			temp.setDuration(duration);
			temp.setRoomType(RoomType.matchGenre(userChoice));
			cart.add(temp);
			System.out.println("\nItem has been successfully added to cart \n");
		}else if(userChoice.equalsIgnoreCase("delux")) {
			temp.setPrice(deluxPrice);
			temp.setDuration(duration);
			temp.setRoomType(RoomType.matchGenre(userChoice));
			cart.add(temp);
			System.out.println("\nItem has been successfully added to cart \n");
		}else if(userChoice.equalsIgnoreCase("suite")){
			temp.setPrice(suitePrice);
			temp.setDuration(duration);
			temp.setRoomType(RoomType.matchGenre(userChoice));
			cart.add(temp);
			System.out.println("\nItem has been successfully added to cart \n");
		}else {
			//return to main menu
			return;
		}
		
	}
	
	//helper function for shopping all transportation services
	private void shopTransportation(String type) {
		//print all services (one type) at once (in version two, separate the flights according to user's entered departure and arrival location)
		
		//first check if there's any unavailable services -> all three types of seats == 0
		checkSeat(type);
		//create a reference to all services
		ArrayList<Service> availList = serviceData.get(type);
		System.out.println("-".repeat(200));
		//print all services for users
		System.out.println("Below are " + type + " you can book: ");
		for(int i = 0; i < availList.size(); i++) {
			System.out.println("\t" + i + ") " + availList.get(i));
			System.out.println();
		}
		//letting user chooses a service
		int userInput = bff.inputInt("\nPlease enter an integer to select a " + type + " to view (enter -1 to return to previous menu)" , -1, availList.size());
		//return to main menu
		if(userInput == -1) {
			return;
		}
		//create a temp of the service the user chooses
		Transportation temp = (Transportation) availList.get(userInput);
		System.out.println();
		System.out.println(temp);
		
		//calculate different seat prices
		double firstPrice;
		double businessPrice;
		double econPrice;
		//in case some seat classes are unavailable, we create a map to store available seat class and its associated price
		Map<String, Double> classPrice = new HashMap<>();
		//print different seat classes and their prices
		if(temp.getAvailableSeat().get(ClassType.FIRST) != 0) {
			firstPrice = temp.getPrice() * ClassType.FIRST.classRatio();
			classPrice.put("first", firstPrice);
			System.out.print("\tFirst Class Price: $" + firstPrice );
			
		}
		if(temp.getAvailableSeat().get(ClassType.BUSINESS) != 0) {
			businessPrice = temp.getPrice() * ClassType.BUSINESS.classRatio();
			classPrice.put("business", businessPrice);
			System.out.print("\tBusiness Class Price: $" + businessPrice );
		}
		if(temp.getAvailableSeat().get(ClassType.ECONOMY) != 0) {
			econPrice = temp.getPrice() * ClassType.ECONOMY.classRatio();
			classPrice.put("economy", econPrice);
			System.out.print("\tEconomy Class Price: $" + econPrice );
			
		}
		
		System.out.println();
		Set<String> choices = classPrice.keySet();
		//letting user chooses a seat class -> only can choose classes which have available seats
		String userChoice = bff.inputWord("\nPlease enter the class you want to book(enter 'none' to return to menu)", choices, "none");
		//if entered none -> return to main menu
		if(userChoice.equalsIgnoreCase("none")) {
			return;
		}
		//change associated fields of the selected service and added it into the cart
		double newPrice = classPrice.get(userChoice);
		temp.setPrice(newPrice);
		temp.setSeatType(ClassType.matchGenre(userChoice));
		temp.getAvailableSeat().put(ClassType.matchGenre(userChoice), temp.getAvailableSeat().get(ClassType.matchGenre(userChoice)) -1);
		this.cart.add(temp);
		System.out.println("\nItem has been successfully added to cart \n");

		
		
		
	}
	
	//helper method to check whether a transportation service is unavailable -> defining as having 0 seat for all classes
	private void checkSeat(String type) {
		ArrayList<Service> val = serviceData.get(type);
		for(int i=0; i < val.size(); i++) {
			Service temp = val.get(i);
			
			if(temp instanceof Transportation) {
				Transportation newTemp = (Transportation) temp;
				Map<ClassType, Integer> seatMap = newTemp.getAvailableSeat();
				if(seatMap.get(ClassType.FIRST).equals(0) && seatMap.get(ClassType.BUSINESS).equals(0) && seatMap.get(ClassType.ECONOMY).equals(0)) {
					val.remove(i);
				}
			}
		}
	}
	
	//helper method to add services into data base -> can only be invoked if the user is an employee
	private void addServiceToDataBase() {
		String input = bff.inputWord("What kind of service do you want to add(please enter 'flight', 'train', 'cruise', or 'hotel')", "flight", "train", "cruise", "hotel");
		if(input.equalsIgnoreCase("flight")) {
			Service temp = createTransportation("flight");
			ArrayList<Service> flightList = serviceData.get("Flights");
			flightList.add(temp);
		}else if(input.equalsIgnoreCase("train")) {
			Service temp = createTransportation("train");
			ArrayList<Service> flightList = serviceData.get("Train");
			flightList.add(temp);
		}else if(input.equalsIgnoreCase("cruise")) {
			Service temp = createTransportation("cruise");
			ArrayList<Service> flightList = serviceData.get("Cruise");
			flightList.add(temp);
		}else { //add hotel
			Service temp = createHotel();
			ArrayList<Service> flightList = serviceData.get("Hotel");
			flightList.add(temp);
		}
		
	}
	
	//helper method for creating a new hotel object
	private Service createHotel() {
		double price = bff.inputDouble("Please enter the price (one night per standard roome)");
		String location = bff.inputLine("Please enter the location of the hotel");
		String provider = bff.inputWord("Please enter the hotel company");
		Service dummy = new Hotel(price, "1", "1", location, provider, RoomType.STANDARD, 1);
		return dummy;
	}
	
	//helper method for creating a new transportation service -> taking in a string to figure out what type of transporation to create
	private Service createTransportation(String type) {
		//get all necessary fields from user
		double price = bff.inputDouble("Please enter the price (economy class price)");
		String startTime = bff.inputWord("Please enter the departure time (in 'mm/dd/yyyy HH:mmAM/PM format')");
		String endTime = bff.inputWord("Please enter the arrival time (in 'mm/dd/yyyy HH;mmAM/PM format')");
		String departureLocation = bff.inputLine("Please enter the departure location");
		String arrivalLocation = bff.inputLine("Please enter the arrival location");
		
		int firstClass = bff.inputInt("Please enter the number of avaiable seats in first class", 0, 1000);
		int businessClass = bff.inputInt("Please enter the number of avaiable seats in business class", 0, 1000);
		int economyClass = bff.inputInt("Please enter the number of avaiable seats in economy class", 0, 1000);
		Map<ClassType, Integer> seatMap = new HashMap<>();
		seatMap.put(ClassType.FIRST, firstClass);
		seatMap.put(ClassType.BUSINESS, businessClass);
		seatMap.put(ClassType.ECONOMY, economyClass);
		
		String airLine = bff.inputLine("Please enter the provider name (e.g. airline, train company)");
		String transNum = bff.inputWord("Please enter the transportation number (e.g. flight number)");
		
		//create the correct type of service
		if(type.equalsIgnoreCase("flight")) {
			Service dummy = new Flights(price, startTime, endTime, departureLocation, arrivalLocation, seatMap, airLine, transNum);
			return dummy;
		}else if(type.equalsIgnoreCase("train")) {
			Service dummy = new Train(price, startTime, endTime, departureLocation, arrivalLocation, seatMap, airLine, transNum);
			return dummy;
		}else {
			Service dummy = new Cruise(price, startTime, endTime, departureLocation, arrivalLocation, seatMap, airLine, transNum);
			return dummy;
		}
		
	}

	//helper method for changing a service's price -> can only be invoked if the user is executive
	private void changePrice() {
		String type = bff.inputWord("Please enter which service type's price you want to change(enter 'Flights', 'Train', 'Cruise', or 'Hotel') !!case sensitive!!"
									, "Flights", "Train", "Cruise", "Hotel");
		ArrayList<Service> temp = serviceData.get(type);
		System.out.println("\nBelow are the type of services you choosed:");
		System.out.println("-".repeat(200));
		for(int i = 0; i < temp.size(); i++) {
			System.out.println("\n" + i + ") " + temp.get(i));
		}
		System.out.println("-".repeat(200));
		int choice = bff.inputInt("Please select a service", 0, temp.size() );
		Service tempServ = temp.get(choice);
		System.out.println("\nThe current price (defualt to economy or standard room) of the service is: $" + tempServ.getPrice() );
		double newPrice = bff.inputDouble("Please enter the new price", 1.0, 9999.99);
		tempServ.setPrice(newPrice);
		System.out.println("\nPrice successfully changed.\n");
	}
	
	//helper method for checking the cart and print total price
	private double checkCart() {
		double totalPrice = 0;
		System.out.println("\nBelow is your cart: ");
		System.out.println("-".repeat(200));
		for (Service a: cart) {
			System.out.print(a);
			totalPrice += a.getPrice();
			System.out.println("\n\tPrice: $" + a.getPrice());
			if(a instanceof Transportation) {
				Transportation temp = (Transportation) a;
				System.out.println(" Seat Class: " + temp.getSeatType().name());
			}else {
				Hotel Temp = (Hotel) a;
				System.out.println(" Room Type: " + Temp.getRoomType().name());
			}
		}
		System.out.println("\nTotal Services: " + cart.size());
		System.out.println("Total Price $" + totalPrice);
		return totalPrice;
	}
	
	//helper method for checkout
	private void checkOut() {
		//tempPayment -> store the amount the user has to pay, change after discount is applied
		double tempPayment = checkCart();
		
		if(currentUser.getClass().getSimpleName().equalsIgnoreCase("Customer")) {
			Customer tempUser = (Customer) this.currentUser;
			if(tempUser.getPremiumLevel() != PremiumLevel.NORMAL) {
				tempPayment *= tempUser.getPremiumLevel().discount();
				System.out.println("-".repeat(200));
				//determine a new price by timing user's discount rate
				System.out.println("Dear " + tempUser.getName() + ", the price you have to pay after your " + 
									Math.round((1.0-tempUser.getPremiumLevel().discount()) * 100) + "% discount is: $" +  tempPayment);
			}
		}else if(currentUser.getClass().getSimpleName().equalsIgnoreCase("Employee")) {
			Employee tempUser = (Employee) this.currentUser;
			tempPayment *= tempUser.getDiscount();
			System.out.println("-".repeat(200));
			//determine a new price by timing user's discount rate
			System.out.println("Dear " + tempUser.getName() + ", the price you have to pay after your " + 
					Math.round((1-tempUser.getDiscount()) * 100) + "% discount is: $" +  tempPayment);
		}else {
			Executive tempUser = (Executive) this.currentUser;
			tempPayment *= tempUser.getDiscount();
			System.out.println("-".repeat(200));
			//determine a new price by timing user's discount rate
			System.out.println("Dear " + tempUser.getName() + ", the price you have to pay after your " + 
					Math.round((1-tempUser.getDiscount()) * 100) + "% discount is: $" +  tempPayment);
		}
		
		boolean pay = bff.inputYesNo("\nDo you want to pay (please enter yes or no)?");
		if(pay) {
			totalPayment = tempPayment;
			if(this.currentUser.getClass().getSimpleName().equalsIgnoreCase("Customer")) {
				//if the user paid and he or she is a customer -> update user's current point & premium level
				Customer temp = (Customer) this.currentUser;
				PremiumLevel tempLevel = temp.getPremiumLevel();
				temp.setCurrentPoints(temp.getCurrentPoints()+(int)totalPayment);
				temp.upGradeLevel();
				if(tempLevel != temp.getPremiumLevel()) {
					//print a message if the user is upgraded to next premium level
					System.out.println("Congratulation! Your account's Premium Level has been upgraded to " + temp.getPremiumLevel().name() 
										+ " ! Your new discount rate will be " + Math.round((1.0-temp.getPremiumLevel().discount()) * 100) + "%");
					
				}
			}
		}else {
			this.exit = true;
		}
		
		if(totalPayment != 0) {
			//creating receipt for user
			System.out.println("\nThanks for choosing Expedia! We have received a total of $" + totalPayment + ". Your recipt can be found at: " + writeReceipt());
			this.exit = true;
		}
	}
	
	//helper method for creating receipt
	private String writeReceipt() {
		//receipt file name
		String fileName = "bin/" + this.currentUser.getName().replace(" ", "_") + "_Receipt.txt";
		//wrtie to file
		try(FileOutputStream fos = new FileOutputStream(fileName);
				PrintWriter out = new PrintWriter(fos)){
				//writing receipt to file
				out.println("Thank you for shopping with Expedia! We have received a total of: $" + this.totalPayment);
				out.print("Name: " + this.currentUser.getName() + "  ");
				out.println("\tAccount Email: " + this.currentUser.getEmail());
				out.println("-".repeat(100));
				
				for(Service s: cart) {
					out.println(s);
				}
				
				out.println("-".repeat(100));
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				out.println("Receipt issued data: " + now.format(formatter));
				return fileName;
			} catch (FileNotFoundException e) {
				System.err.println("File not found, problem saving data... " + fileName);
				e.printStackTrace();
				return "Sorry, a problem occured while generating your receipt";
			} catch (IOException e) {
				System.err.println("Some other IO Problem happened while saving data... " + e);
				e.printStackTrace();
				return "Sorry, a problem occured while generating your receipt";
			}
		
	
	}

	public static void main(String[] args) {
		UserInter test = new UserInter();
		test.run();
	}
}
