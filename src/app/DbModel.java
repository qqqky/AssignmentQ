package app;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sqlite.SQLiteDataSource;

import dto.ResponseCustList;
import dto.ResponseWaitTime;
import dto.Customer;
import dto.Response;
import dto.TimeSlotIDPair;

public class DbModel {
	
	private final SQLiteDataSource ds = new SQLiteDataSource();
	public enum TimeStatus  {FREE, RESERVED, ONGOING, CANCELLED, SERVICED};
	public enum ObjectStatus {ADMIN, CUSTOMER};
	private String[] specNames = {"Amy", "Betty", "Edward", "Emma", "Gary", "John", "Kevin", "Mary", "Richard", "Sarah"};
	private String[] specLast = {"Jones", "Smith", "Miller", "Moore", "Clark", "Carter", "Reed", "Watson", "Myers", "Foster"};
	private String[] times = {"09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", 
			"11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45",
			"13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45",
			"15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45"};
	private Map<Integer, List<String>> internalTimeMap = new HashMap<>();
	private SecureRandom rand = new SecureRandom();
	private List<Integer> randomizerHelper;
	private LocalTime startOfDay = LocalTime.of(8, 55);
	private LocalTime current = startOfDay;
	private LocalDate date = LocalDate.now();
	private static int currentCode = -1;
	private static boolean canServeCustomers = true;
	private int specialistCount = 5;
	private DisplayController controller;
	
	private final ObjectStatus currentStatus;

	public static void main(String[] args) {
		
		
	}
	private DbModel() {
		currentStatus = ObjectStatus.ADMIN;
		ds.setUrl("jdbc:sqlite:"+"reg.db");
	}
	private DbModel(DisplayController controller, LocalDate date) {
		this.controller = controller;
		this.date = date;
		this.initializeEmptyMap();
		currentStatus = ObjectStatus.ADMIN;	
		ds.setUrl("jdbc:sqlite:"+"reg.db");
	}
	public static DbModel create(DisplayController controller, LocalDate date) {
		return new DbModel(controller, date);
	}
	/**
	 * Only for SPECIALISTS table
	 * @param tableName - specialist id
	 */
	private void printSpecialistsTable() {
		String table = "SPECIALISTS";
		
		try(Connection con = ds.getConnection()){
			
			con.setAutoCommit(false);
			
			try(PreparedStatement pstmt = con.prepareStatement(
					"SELECT * FROM "+table)){
			
				
				ResultSet results = pstmt.executeQuery();
			
				while(results.next()) {
					int id = results.getInt("ID");
					String name = results.getString("name");
					String lastName = results.getString("last_name");
					//String pass = results.getString("password");
					String username = results.getString("username");
					
					System.out.println("ID: "+id+"\tname: "+name+"\tlastName: "+lastName+"\tusername: "+username);
					
				}
				con.commit();
				results.close();
			}catch(SQLException e) {
				System.err.println("Could not retrieve full table view");
				e.printStackTrace();
			}
		}catch(SQLException e) {
			System.err.println("Could not retrieve full table view");
			e.printStackTrace();
		}
	}
	/**
	 * Only for timetable retrieval (not used)
	 * @param numSpecialist - specialist ID
	 */
	void printTimeTable(int numSpecialist) {
		
		String table = "TIMETABLE_"+this.to2DigitString(numSpecialist);
		
		try(Connection con = ds.getConnection()){
			
			con.setAutoCommit(false);
		
			try(PreparedStatement pstmt = con.prepareStatement(
					"SELECT * FROM "+table)){
			
				ResultSet results = pstmt.executeQuery();
			
				System.out.println(table+"(specialist: "+specNames[numSpecialist-1]+" "+specLast[numSpecialist-1]+" - teller id "+numSpecialist+")");
				
				while(results.next()) {
					LocalTime time = results.getTime("visit_time").toLocalTime();
					String name = results.getString("name");
					String lastName = results.getString("last_name");
					String resCode = results.getString("res_code");
					String status = results.getString("status");
					LocalDate date = results.getDate("registration_date").toLocalDate();
					
					System.out.println("visit_time: "+time+"\tname: "+name+"\tlastName: "+lastName+"\tcode: "+resCode+"\tstatus: "+status+"\tdate:"+date);
					
				}
				con.commit();
				results.close();
			}catch(SQLException e) {
				System.err.println("Error retrieving time table");
				e.printStackTrace();
			}
		}catch(SQLException e) {
			System.err.println("Could not retrieve full time table view");
			e.printStackTrace();
		}
	}
	private String to2DigitString(int input) {
		
		if(input>=100 || input < 0) {
			throw new UnsupportedOperationException("This value is not supported");
		}
		String ID = "";
		if(input<10) {
			ID = "0"+input;
		}else {
			ID = ""+input;
		}
		
		return ID;
		
	}
	private String to3DigitString(int input) {
		
		if(input>=1000 || input < 0) {
			throw new UnsupportedOperationException("This value is not supported");
		}
		
		String ID = "";
		if(input<10) {
			ID = "00"+input;
		}else if(input<100) {
			ID = "0"+input;
		}else {
			ID = ""+input;
		}
		
		return ID;
		
	}
	final String registerCustomer(String name, String lastName) {
		
		String reservationCode = "";
		
		//before any registration, we remove all currently unavailable times from internalTimeMap
		this.updateFilteredTimes();
		TimeSlotIDPair bestChoice = this.findNearestTimeSlot();
		
		if(bestChoice.isEmpty()) {
			reservationCode = "Sorry, no more free space left for today";
			this.canServeCustomers = false;
		
		}else {
			
			reservationCode = this.insertCustomerToTimeTable(bestChoice.getID(), bestChoice.getTimeSlot(), name, lastName);
			controller.supplyQueuePosition(bestChoice.getID(), reservationCode);
		}
	
		return reservationCode;
	}
	//specialist ID is interchangeable with 'window number'
	final boolean changeCustomerStatus(String visitTime, TimeStatus newStatus, int specialistID) {
			
		boolean check = Arrays.stream(times).anyMatch(time -> time.equals(visitTime));
		LocalTime time;
			
		if(!check) throw new UnsupportedOperationException("Wrong time input");
		else {
			time = LocalTime.parse(visitTime);
		}
		boolean result = false;
			
		//check current status before changing
		TimeStatus status = newStatus;
			
	/*
	 * Can only change to FREE status if that time hasnt't passed yet
	 */
		if(status == TimeStatus.FREE && time.isBefore(current)) {
			System.err.println("Cannot change status to FREE. That time has already passed");
			return false;
		}
			
	/*
	 * If specialist wants to mark the visit as ongoing - allow some range (+-10min)
	 * (maybe the customer got there early or was too late)
	 */
		if(status == TimeStatus.ONGOING && !time.isBefore(current.minusMinutes(10))
				&& !time.isAfter(current.plusMinutes(10))) {
				
			//also check if no ongoing visits already
			if(this.ongoingVisitExists(specialistID)) {
				return false;
			}
		}
			
		String query = "UPDATE TIMETABLE_"+this.to2DigitString(specialistID)
		+ " SET status = ? "
		+ " WHERE visit_time = ?";
			
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query)){
				
			con.setAutoCommit(false);
				
			pstmt.setString(1, newStatus.toString());
			pstmt.setTime(2, java.sql.Time.valueOf(time));
			
			pstmt.executeUpdate();
			con.commit();
			result = true;
				
		}catch(SQLException e) {
			System.err.println("Error changing customer status for window "+specialistID);
			e.printStackTrace();
		}
			
		//if status changed to FREE - notify
		if(status == TimeStatus.FREE && this.currentStatus == ObjectStatus.ADMIN) {	
			DbModel.canServeCustomers = true;
			this.internalTimeMap.get(specialistID).add(time.toString());
			System.out.println("Time slot freed up for specialist: "+specialistID+" at time "+time);
		}
			//if customer has been serviced (status changed to SERVICED) - add to customer database
		if(status == TimeStatus.SERVICED && this.currentStatus == ObjectStatus.ADMIN) {		
			this.addCustomerToLogTable(visitTime, specialistID);
		}
		return result;
	}
	/*
	 * Adds a customer to customer 'log' table (not the daily visit table) if they have been serviced successfully
	 * Eg. as a future reference for company customer data/ recurring customers
	 */
	private final void addCustomerToLogTable(String visitTime, int specialistID) {
			
			
		LocalTime time = LocalTime.parse(visitTime);
		LocalTime vTime = null;
		String name = null;
		String lastName = null;
		LocalDate vDate = null;
			
			
			String table = "TIMETABLE_"+this.to2DigitString(specialistID);
			
			//1. fetch visitation info based on visitation time
			try(Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
						"SELECT visit_time, name, last_name, registration_date FROM "+table
						+" WHERE visit_time = ?")){
				
				con.setAutoCommit(false);
				pstmt.setTime(1, java.sql.Time.valueOf(time));
				ResultSet results = pstmt.executeQuery();
				
				//should only match a single line
				while(results.next()) {
					vTime = results.getTime(1).toLocalTime();
					name = results.getString(2);
					lastName = results.getString(3);
					vDate = results.getDate(4).toLocalDate();
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			table = "CUSTOMERS";
			//2. Add customer to customer table
			try(Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO "+table +" VALUES (?, ?, ?, ?, ?);")){
				con.setAutoCommit(false);
				pstmt.setInt(1, specialistID);
				pstmt.setString(2, name);
				pstmt.setString(3, lastName);
				pstmt.setDate(4, java.sql.Date.valueOf(vDate));
				pstmt.setTime(5, java.sql.Time.valueOf(vTime));
				
				pstmt.executeUpdate();
				con.commit();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
	}
	private final void sortTimes() {
		int length = internalTimeMap.size();
		
		for(int i=0; i<length; i++) {
			Collections.sort(internalTimeMap.get(i+1));
		}
	}
	
	private final void initializeEmptyMap() {
		
		String[] listBasedOnCurrentTime = this.filterAvailableTimes().toArray(new String[0]);
		
		for(int i=0; i<specialistCount;i++) {
			internalTimeMap.put(i+1, new ArrayList<String>());
			for(int j=0; j<listBasedOnCurrentTime.length;j++) {
				internalTimeMap.get(i+1).add(listBasedOnCurrentTime[j]);
			}
		}
	}
	private final TimeSlotIDPair findNearestTimeSlot() {
		
		this.sortTimes();
		String bestTime = "zzzz";
		int bestID = -1;
		String currentTime = "";
		
		/*
		 * First, find lowest time slot
		 */
		
			for(int i=0; i<internalTimeMap.size(); i++) {
				if(!internalTimeMap.get(i+1).isEmpty()) {	//do not engage if empty already
					currentTime = internalTimeMap.get(i+1).get(0);	//but if not, then only look at first index
					
					if(currentTime!=null) {
						
						if(currentTime.compareTo(bestTime)<=0) {
							bestTime = currentTime;	//only interested in this for now
							bestID = i+1;
						}						
					}
				}
				
			}	
		
		/*
		 * We found nearest empty time slot, but multiple specialists can have that,
		 * so to make it fair, need to randomize which specialist is chosen (eg.: as all of them are free at first)
		 */
		String item = "";
		if(bestID == -1) { //bestID == -1 only if all lists are empty (no more free space)
			return TimeSlotIDPair.empty();
		}
		else {
			if(randomizerHelper == null) {
				randomizerHelper = new ArrayList<>();
			}
			//now filter in all specialists that have same best times
			for(int i=0; i<internalTimeMap.size();i++) {
				if(!internalTimeMap.get(i+1).isEmpty()) {	//avoid ones that are empty
					item = internalTimeMap.get(i+1).get(0);
					if(item !=null && item.equals(bestTime)) {
						randomizerHelper.add(i+1);	//will represent all specialists (IDs) that have closest time slot available
					}
				}
			}
		
		/*
		 * Once we have the list - choose the specialist randomly
		 * (if reached here, randomizerHelper should never be empty)
		 */
			int bound = randomizerHelper.size();
		
			int rng = rand.nextInt(bound);
			int chosenSpecialist = randomizerHelper.get(rng);
			randomizerHelper.clear(); //cleanup helper
		
			internalTimeMap.get(chosenSpecialist).remove(bestTime); //remove from available times
			
			
			return new TimeSlotIDPair(chosenSpecialist, bestTime);
			
		}		
	}
	/*
	 * Only allow reservation codes (0)0-99
	 */
	private static final int getReservationCode() {
		
		if(currentCode==999) {
			currentCode=0;
		}else {
			currentCode++;
		}
		
		return currentCode;
	}
	private String insertCustomerToTimeTable(int specialistID, String timeSlot, String name, String lastName) {
		
		
		LocalTime time = LocalTime.parse(timeSlot);
			if(time.isBefore(current.minusMinutes(3))) {
			return "Cannot add customer. The time has passed already";
		}
		
		String table = "TIMETABLE_"+this.to2DigitString(specialistID);
		String reservationCode = this.to3DigitString(DbModel.getReservationCode());
		
				
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("UPDATE "+table
					+ " SET name = ? , last_name = ? , res_code = ? , status = ?"
					+ " WHERE visit_time = ?")){
			con.setAutoCommit(false);
			
			pstmt.setString(1, name);
			pstmt.setString(2, lastName);
			pstmt.setString(3, reservationCode);
			pstmt.setString(4, TimeStatus.RESERVED.toString());
			pstmt.setTime(5, java.sql.Time.valueOf(time));
			
			pstmt.executeUpdate();
			con.commit();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reservationCode;
	}
	/**
	 * Make sure only future times are offered
	 * @return List<String> - only times that are past or equal to the current time
	 */
	private final List<String> filterAvailableTimes() {
		
		LocalTime currentTime = current;
		
		List<String> filteredTimes = Arrays.stream(times).filter(str -> str.compareTo(currentTime.toString())>=0).collect(Collectors.toList());
		
		//System.out.println("FilteredTimes: \r\n"+filteredTimes);
		
		return new ArrayList<>(filteredTimes);
	}
	/*
	 * Do not offer unavailable times (eg. after a customer cancelled)
	 */
	public void updateFilteredTimes() {
		
		List<String> unavailableTimes = this.filterUnavailableTimes();
		for(String unavailable: unavailableTimes) {
			for(int i=0; i<internalTimeMap.keySet().size(); i++) {
				if(internalTimeMap.get(i+1).contains(unavailable)) {
					internalTimeMap.get(i+1).remove(unavailable);
				}
			}
		}
	}
	/**
	 * Helper method, opposite of the one above
	 * @return List<String> - only times that are before current time
	 */
	private final List<String> filterUnavailableTimes() {
		
		LocalTime currentTime = current;
		List<String> filteredTimes = Arrays.stream(times).filter(str -> str.compareTo(currentTime.toString())<0).collect(Collectors.toList());
		
		return new ArrayList<>(filteredTimes);
	}
	
	static final boolean getCanServe() {
		return canServeCustomers;
	}
	Response timeLeftUntilVisit(String code, String lastName) {
		
		LocalTime time = current;
		LocalTime visitTime = null;
		String answer = "";
		
		//no info about specialist ID is retained, so we must search all tables
		for(int i=0; i<this.getSpecialistCount(); i++) {
			String table = "TIMETABLE_"+this.to2DigitString(i+1);
		
			try(Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
				"SELECT visit_time FROM "+table+
				" WHERE last_name = ? AND res_code = ? AND status = ?")){
			
				con.setAutoCommit(false);
				pstmt.setString(1, lastName);
				pstmt.setString(2, code);
				pstmt.setString(3, TimeStatus.RESERVED.toString());
				
				ResultSet results = pstmt.executeQuery();
			
				if(results.next()) {
					visitTime = results.getTime("visit_time").toLocalTime();
				}	
				con.commit();
				results.close();
			}catch(SQLException e) {
				System.err.println("Error finding visit time in #timeLeftUntilVisit()");
				e.printStackTrace();
			}
		}
		
		if(visitTime == null) {	//if time not found
			return new ResponseWaitTime("No such entry found");
		}
		
		int result = visitTime.toSecondOfDay() - time.toSecondOfDay();
			
	/*
	 * Evaluate the result, give some margin for customers
	 */
		if(result <-300) {
			answer = "Sorry, your visit has already passed";
		}else if(result <0) {
			answer = "Your visit is ongoing. Hurry up";	
		}else {
			int minutes = result/60;
			answer = "Waiting time is "+minutes+" minutes";
		}
		
	/*
	 * Since searching for waiting time without knowing specialist's ID takes a long time,
	 * we could keep a map for later retrieval
	 */
		
		return new ResponseWaitTime(answer);
	}
	
	private final void changeStateOfAllUnavailableTimes() {
		
		List<String> unavailableList = this.filterUnavailableTimes();
		
		for(String item: unavailableList) {
			for(int i = 0; i<specialistCount;i++) {
				this.changeCustomerStatus(item, TimeStatus.SERVICED, i+1);
			}	
		}	
	}
	
	
	/**
	 * Retrieves full today's schedule (customer list) for the specified specialist ID
	 * @return CustListResponse - custom DTO for convenient toString()
	 */
	ResponseCustList retrieveCustomerList(int numSpecialist) {

		String table = "TIMETABLE_"+this.to2DigitString(numSpecialist);
		//for more convenient response format
		ResponseCustList response = new ResponseCustList();
		
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(
					"SELECT * FROM "+table)){
			
			
				con.setAutoCommit(false);
			
			ResultSet results = pstmt.executeQuery();
			
		while(results.next()) {
			LocalTime time = results.getTime("visit_time").toLocalTime();
			String name = results.getString("name");
			String lastName = results.getString("last_name");
			String resCode = results.getString("res_code");
			String status = results.getString("status");
			LocalDate date = results.getDate("registration_date").toLocalDate();
						
				
			response.add(new Customer(time, name, lastName, resCode, status, date));
				
		}
				con.commit();
				results.close();
				
		}catch(SQLException e) {
			System.err.println(e.getMessage());
				System.err.println("Error retrieving time table");
				e.printStackTrace();
		}
		return response;
	}
	final String retrieveSpecialistNameByID(int id) {
		
		String table = "SPECIALISTS";
		String answer = "";
		
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(
					"SELECT name FROM "+table
					+" WHERE ID = ?")){
			
				con.setAutoCommit(false);
				pstmt.setInt(1, id);
				ResultSet result = pstmt.executeQuery();
				
				if(result.next()) {
					answer = result.getString("name");
				}
				result.close();
				con.commit();
		}catch(SQLException e) {
			System.err.println(e.getMessage());
				System.err.println("Error retrieving specialist name");
				e.printStackTrace();
		}
		return answer;
		
	}
	/**
	 * Visit time can be either marked CANCELLED or FREE, based on current time
	 * @param code
	 * @param lastName
	 */
	boolean findAndCancelVisitByCustomerName(String code, String lastName) {
		
		LocalTime time = current;
		LocalTime visitTime = null;
		int tableNum = -1;
		boolean result = false;

		//no info about specialist ID is retained, so we must search all tables
		for(int i=0; i<this.getSpecialistCount(); i++) {
			tableNum = i+1;
			String table = "TIMETABLE_"+this.to2DigitString(i+1);
		
			try(Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
				"SELECT visit_time FROM "+table+
				" WHERE last_name = ? AND res_code = ? AND status = ?")){
			
				con.setAutoCommit(false);
				pstmt.setString(1, lastName);
				pstmt.setString(2, code);
				pstmt.setString(3, TimeStatus.RESERVED.toString());
				
				ResultSet results = pstmt.executeQuery();
			
				if(results.next()) {
					visitTime = results.getTime("visit_time").toLocalTime();
					break;
				}	
				con.commit();
				results.close();
			}catch(SQLException e) {
				System.err.println("Error finding visit time and customer in #findAndCancelVisitByCustomerName()");
				e.printStackTrace();
			}
		}	
		//once we have specialistID (tableNum) and visit time, can cancel the appointment (or set to FREE)
		
		if(visitTime == null) {	//if not found
			return false;
		}
		int resultMins = (visitTime.toSecondOfDay() - time.toSecondOfDay())/60;
			
			String table = "TIMETABLE_"+this.to2DigitString(tableNum);
			
			try(Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
				"UPDATE "+table+
				" SET status = ? WHERE visit_time = ? AND last_name = ?")){
			
				con.setAutoCommit(false);
				if(resultMins<=0) {			//won't allow any margin of error when customer cancels 'just in time'
					pstmt.setString(1, TimeStatus.CANCELLED.toString());
				}else {
					pstmt.setString(1, TimeStatus.FREE.toString());
				}
				pstmt.setTime(2, java.sql.Time.valueOf(visitTime));
				pstmt.setString(3, lastName);
				
				pstmt.executeUpdate();
				con.commit();
				result = true;
				
			}catch(SQLException e) {
				System.err.println("Error resetting status in #findAndCancelVisitByCustomerName()");
				e.printStackTrace();
			}
			
		
		return result;
			
	}
	private boolean ongoingVisitExists(int specialistID) {
		
		String table = "TIMETABLE_"+this.to2DigitString(specialistID);
		boolean result = false;
			
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(
				"SELECT visit_time FROM "+table+" WHERE status = ?")){
			
			
				con.setAutoCommit(false);
				pstmt.setString(1, "OCCUPIED");
		
				ResultSet results = pstmt.executeQuery();
		
		//if at least 1 result found - ongoing visit exists
		if(results.next()) {
		result = true;	
		}
						
		con.commit();
		results.close();
				
		}catch(SQLException e) {
				System.err.println("Error in ongoingVisitExists()");
				e.printStackTrace();
		}
		return result;
	}
	
	int getSpecialistCount() {
		return this.specialistCount;
	}
	void synchronize(LocalTime time) {
		current = time;
		//System.out.println("DbModel synchronized to time: "+current);
	}
	
	int checkCredentials(String username, String password) {
		
		String table = "SPECIALISTS";
		
		int answer = -1;
		
		try(Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(
			"SELECT * FROM "+table+
			" WHERE username = ? AND password = ?")){
		
			con.setAutoCommit(false);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet results = pstmt.executeQuery();
			
			if(results.next()) {
				answer = results.getInt("ID");
			}else {
				answer = -1;
			}
			con.commit();
			results.close();
			
		}catch(SQLException e) {
			System.err.println("Error in ongoingVisitExists()");
			e.printStackTrace();
		}
			
		return answer;
	}
	
}
