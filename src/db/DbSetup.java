package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.sqlite.SQLiteDataSource;


/*
 * Generator class for a new clean DB.
 * New specialists can only be added though this class directly.
 * 
 * Possible visitation times are set 15mins apart from 9:00 to 16:45.
 * If company policy is different, admins can easily change that.
 * 
 * Database is initialized as blueprint for current day, with all visitation times set to FREE. 
 */
public class DbSetup {
	
	private final SQLiteDataSource ds = new SQLiteDataSource();
	private String[] specNames = {"Amy", "Betty", "Edward", "Emma", "Gary"};//, "John", "Kevin", "Mary", "Richard", "Sarah"};
	private String[] specLast = {"Jones", "Smith", "Miller", "Moore", "Clark"};//, "Carter", "Reed", "Watson", "Myers", "Foster"};
	private String[] times = {"09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", 
								"11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45",
								"13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45",
								"15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45"};
	
	private enum TimeStatus  {FREE, OCCUPIED, ONGOING, SERVICED};

	public static void main(String[] args) throws SQLException{
		
		DbSetup db = new DbSetup("reg.db");
		db.initializeNewDb();
		db.populateDayTables(LocalDate.now());
		
		System.out.println("Database has been created");
	}
	private DbSetup(String name) {
		init(name);
	}
	
	private void init(String name) {
		this.getSource().setUrl("jdbc:sqlite:"+name);
	}
	private final void initializeNewDb() {
		
		this.createEmptyTables();
		for(int i=0; i<specNames.length; i++) {
			this.addSpecialist("specialists", (i+1), specNames[i], specLast[i], specNames[i], "12345");
		}
		
	}
	private void createEmptyTables() {
		
		try(Connection con = this.getSource().getConnection()){
			
			con.setAutoCommit(false);
		
			try{
				PreparedStatement pstmt = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS SPECIALISTS( "
					+ "ID INTEGER PRIMARY KEY, "
					+ "name TEXT, "
					+ "last_name TEXT, "
					+ "username TEXT, "
					+ "password TEXT"
					+ ");");
					
					pstmt.executeUpdate();
					con.commit();
					pstmt.close();
					
					
			}catch(SQLException e) {
				System.err.println("Could not createTable in db");
				e.printStackTrace();
			}
					
			try{
				PreparedStatement pstmt2 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS CUSTOMERS( "
					+ "specialist_id INTEGER, "
					+ "cust_name TEXT, "
					+ "cust_last_name TEXT, "
					+ "visit_date DATE, "
					+ "visit_time TIME); ");
					
					pstmt2.executeUpdate();
					con.commit();
					pstmt2.close();
					
			}catch(SQLException e) {
				System.err.println("Could not createTable in db");
				e.printStackTrace();
			}
		
		}catch(SQLException e) {
			System.err.println("Error creating some table");
			e.printStackTrace();
		}			
			
	}
	private SQLiteDataSource getSource() {
		return this.ds;
	}
	private void addSpecialist(String tableName, int ID, String name, String lastName, String username, String password){
	
		PreparedStatement pstmt = null;
		String table = this.getTableName(tableName);
		
		try(Connection con = ds.getConnection()){
				con.setAutoCommit(false);
				
			pstmt = con.prepareStatement("INSERT INTO "+ table +
            " VALUES (?, ?, ?, ?, ?); ");
			pstmt.setInt(1, ID);
			pstmt.setString(2, name);
			pstmt.setString(3, lastName);
			pstmt.setString(4, username);
			pstmt.setString(5, password);
			pstmt.executeUpdate();
			con.commit();
			pstmt.close();
		}catch(SQLException e) {
			System.err.println("Could not insert the statement into db");
		}
		
		//also create daily customer data for this specialist
		this.createCustomerTableBlueprintForSpecialistID(ID);
			
	}
	private String getTableName(String name) {
		return name.toUpperCase();
	}
	private void createCustomerTableBlueprintForSpecialistID(int id) {
		
		String ID = this.to2DigitString(id);
		
		try(Connection con = this.getSource().getConnection()){
			
			con.setAutoCommit(false);
		System.out.println("TIMETABLE_"+ID);
			try{
				PreparedStatement pstmt = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS TIMETABLE_"+ID+"( "
					+ "visit_time TIME PRIMARY KEY,"
					+ "name TEXT, "
					+ "last_name TEXT, "
					+ "res_code TEXT, "
					+ "status TEXT, "
					+ "registration_date DATE"
					+ ");");
					
					pstmt.executeUpdate();
					con.commit();
					pstmt.close();
					
					
			}catch(SQLException e) {
				System.err.println("Could not create visit table for specialistID="+id);
				e.printStackTrace();
			}
		}catch(SQLException e) {
			System.err.println("Could not createTable in db");
			e.printStackTrace();
		}
	}
	private void populateDayTables(LocalDate date) {
		
		int length = specNames.length;
		for(int i=0; i<length; i++) {
			this.insertPlaceholdersToTimeTable(i+1, date);
		}
	}
	private void insertPlaceholdersToTimeTable(int specialistID, LocalDate forDate) {
		
		if(forDate == null) {
			forDate = LocalDate.now();	
		}
		System.out.println(forDate);
		String table = "TIMETABLE_"+this.to2DigitString(specialistID);
		LocalTime time;
		LocalDate date;

		try(Connection con = ds.getConnection()){
			con.setAutoCommit(false);	
			
			for(int i=0; i<times.length; i++) {
				try(PreparedStatement pstmt = con.prepareStatement("INSERT INTO "+ table +
	            " VALUES (?, ?, ?, ?, ?, ?) ")){
				
				time = LocalTime.parse(times[i]);
				java.sql.Date sqlDate = java.sql.Date.valueOf(forDate);
				java.sql.Time sqlTime = java.sql.Time.valueOf(time);
				
				pstmt.setTime(1, sqlTime);
				pstmt.setString(2, "");
				pstmt.setString(3, "");
				pstmt.setString(4, "");
				pstmt.setString(5, TimeStatus.FREE.toString());
				pstmt.setDate(6, sqlDate);
				pstmt.executeUpdate();
				con.commit();
				pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}		
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}	
			
	}
	private String to2DigitString(int input) {
		
		String ID = "";
		if(input<10) {
			ID = "0"+input;
		}else {
			ID = ""+input;
		}	
		return ID;	
	}

}

