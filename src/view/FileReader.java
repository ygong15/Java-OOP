package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import model.*;

/**
 * 
 * FileReader class is in charge of reading service file and user file.
 * Return a map (service file reader) and a set (user file reader) to further use as data bases 
 *
 */
public class FileReader {
	//two file names: static and final
	private static final String SERVICE_FILE_NAME = "bin/Services.tsv";
	private static final String USER_FILE_NAME = "bin/Users.tsv";
	
	//service file reader
	public static Map<String, ArrayList<Service>> readService(){
		//create an empty map
		Map<String, ArrayList<Service>> serviceMap = new HashMap<>();
		//create 4 different array list -> value of the map
		ArrayList<Service> flightList = new ArrayList<>();
		ArrayList<Service> trainList = new ArrayList<>();
		ArrayList<Service> cruiseList = new ArrayList<>();
		ArrayList<Service> hotelList = new ArrayList<>();
		//put array lists associated with their keys into the map
		serviceMap.put("Flights", flightList);
		serviceMap.put("Train", trainList);
		serviceMap.put("Cruise", cruiseList);
		serviceMap.put("Hotel", hotelList);
		//start file reading
		try(FileInputStream reader = new FileInputStream(SERVICE_FILE_NAME); 
				Scanner fs = new Scanner(reader)){
				fs.nextLine();
				while(fs.hasNextLine()) {
					//use a scanner to read line by line
					String line = fs.nextLine();
					//create a helper scanner
					Scanner helper = new Scanner(line);
					helper.useDelimiter("\t");
					//get the service type with the helper scanner
					String type = helper.next().toUpperCase();
					//use a switch statement to check what type of service is in the line thus adding it into the correct array
					//the switch below calls a helper method to create the actual service
					switch(type) {
						case "FLIGHT":
							Service f = readTransportation(line);
							List<Service> targetF = serviceMap.get("Flights");
							targetF.add(f);
							break;
						case "TRAIN":
							Service t = readTransportation(line);
							List<Service> targetT = serviceMap.get("Train");
							targetT.add(t);
							break;
						case "CRUISE":
							Service c = readTransportation(line);
							List<Service> targetC = serviceMap.get("Cruise");
							targetC.add(c);
							break;
						case "HOTEL":
							Service h = readHotel(line);
							List<Service> targetH = serviceMap.get("Hotel");
							targetH.add(h);
							break;
						default:
							System.err.println("The service type is not acceptable");
							break;
							
					}
						

				}
		}
		catch (FileNotFoundException e) {
            System.err.println("File not found: " + SERVICE_FILE_NAME);
         
        } catch (IOException e) {
            System.err.println("An IOException occured. Can't recover, ending program. " );
            e.printStackTrace();
            System.exit(1);
        }
		return serviceMap;
	}
	
	//helper method to create hotel
	private static Service readHotel(String line) {
		//read in values
		Scanner hs = new Scanner(line);
		hs.useDelimiter("\t");
		String type = hs.next();
		String startTime = hs.next();
		int duration = hs.nextInt();
		double price = hs.nextDouble();
		String location = hs.next();
		
		String dummy1 = hs.next();
		String dummy2 = hs.next();
		String dummy3 = hs.next();
		String dummy4 = hs.next();
		
		String provider = hs.next();
		String dummy5 = hs.next();
		
		//create a hotel object (all hotel's duration is 1 night -> filled in as "endTime" & RoomType for the default price is STANDARD
		Service dummy = new Hotel(price, startTime, startTime, location, provider, RoomType.STANDARD, duration);
		
		return dummy;
	}

	//helper method to create transportation services
	private static Service readTransportation(String line) {
		//create a new scanner
		Scanner ts = new Scanner(line);
		ts.useDelimiter("\t");
		//read in values
		String type = ts.next();
		String startTime = ts.next();
		String endTime = ts.next();
		double price = ts.nextDouble();
		String destination = ts.next();
		String departure = ts.next();
		
		int firstClassNum = ts.nextInt();
		int businessClassNum = ts.nextInt();
		int economyClassNum = ts.nextInt();
		//construct a map for transportation services
		Map<ClassType, Integer> seatMap = new HashMap<>();
		seatMap.put(ClassType.FIRST, firstClassNum);
		seatMap.put(ClassType.BUSINESS, businessClassNum);
		seatMap.put(ClassType.ECONOMY, economyClassNum);
		
		String provider = ts.next();
		String code = ts.next();
		
		//create service based on the type of service it is 
		if(type.equalsIgnoreCase("Flight")) {
			Service dummy = new Flights(price, startTime, endTime, departure, destination, seatMap, provider, code);
			return dummy;
		}else if(type.equalsIgnoreCase("Train")){
			Service dummy = new Train(price, startTime, endTime, departure, destination, seatMap, provider, code);
			return dummy;
		}else{
			Service dummy = new Cruise(price, startTime, endTime, departure, destination, seatMap, provider, code);
			return dummy;
		}

	}
	
	public static Set<User> readUser(){
		Set<User> userSet = new HashSet<>();
		try(FileInputStream reader = new FileInputStream(USER_FILE_NAME); 
				Scanner fs = new Scanner(reader)){
				fs.nextLine();
				while(fs.hasNextLine()) {
					//use a scanner to read line by line
					String line = fs.nextLine();
					userSet.add(createUser(line));
				}
		}
		catch (FileNotFoundException e) {
            System.err.println("File not found: " + SERVICE_FILE_NAME);
         
        } catch (IOException e) {
            System.err.println("An IOException occured. Can't recover, ending program. " );
            e.printStackTrace();
            System.exit(1);
        }
		return userSet;
		
	}

	private static User createUser(String line) {
		Scanner us = new Scanner(line);
		us.useDelimiter("\t");
		
		String type = us.next();
		String email = us.next();
		String name = us.next();
		String passWord = us.next();
		String id = us.next();
		int currentPoint = us.nextInt();
		
		if(type.equalsIgnoreCase("Customer")) {
			User cus = new Customer(email, name, passWord, currentPoint);
			return cus;
		}else if(type.equalsIgnoreCase("Employee")) {
			Employee emp = new Employee(email, name, passWord, id);
			return emp;
		}else {
			Executive exe = new Executive(email, name, passWord, id);
			return exe;
		}
		
	}
	
}
