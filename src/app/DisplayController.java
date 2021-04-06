package app;

import java.time.LocalDate;
import java.time.LocalTime;

import app.DbModel.TimeStatus;
import display.CustomerGUIController;
import display.SpecialistClient;
import display.SpecialistGUIController;
import dto.Client;
import dto.CmdGetWaitingTime;
import dto.CmdLogin;
import dto.CmdMarkVisitBegan;
import dto.CmdMarkVisitCancelled;
import dto.CmdMarkVisitEnded;
import dto.CmdRegistrationRequest;
import dto.CmdRetrieveCustomerList;
import dto.Command;
import dto.ResponseCustList;
import dto.ResponseToRegistration;
import dto.Response;
import dto.ResponseBool;

/**
 * This class is the main controller class.
 * It is also the entry point to the program
 * 
 * Make sure DB is initialized beforehand and is reachable for name "reg.db".
 * Empty blueprint database can be easily be initialized by launching the main method of db/DbSetup.java class.
 * 
 * Class explanation:
 * 	app/DbModel is the main logic class, which is controlled by this class (DisplayController).
 * 	CustomerClient, as the name says is the logic representation of CustomerGUIController
 * 	SpecialistClient is the same, but for SpecialistGUIController
 * 
 * 	Only DisplayController has direct access to DbModel.
 * 
 * Input parameters in most cases aren't strictly checked (just enough to verify the GUI is working as intended).
 * There is also no bogus input control and any gibberish will be accepted as name/last name for customer registration,
 * 		as long as both of the fields are not completely empty.
 * To cancel a visit, customer must supply the code and his last name.
 * 
 * Additional info:
 * 		For simplicity reasons all passwords for the 5 registered specialists are: 12345
 * 		All usernames are their own names (see DbSetup class)
 * 		Example: login credentials of 1st specialist are: Amy 12345
 * 		Passwords are not hashed
 * 
 * 		GUI has "Spawn" button that spawns a random customer (name is Bobby+integer, last name is Bobson+integer - eg.:
 * 		Bobby0 Bobson0 will always be the first one spawned. His code is shown in the queue simulation.
 * 		If program is restarted, codes are again started from value 000 (even though DB can already contain them).
 * 		Similarly, the random customer will also start from Bobby0 Bobson0 - the database is not checked for existing
 * 		entries for this (only allowed time is being checked).
 * 		+1/-1 buttons are used to increment/decrement the timer (eg. customer cannot cancel a visit if the visit is already
 * 		over (some margin of 'being late' is allowed).
 * 
 * 		Program will not register customers to time slots that are already (after some allowed margin) in the past.
 * 
 * 		The easiest way to view the important contents of the DB is to login any specialist and click "Refresh List".
 * 
 * Not implemented:
 * 		Time management was not fully implemented due to time limitations on the project (the clock always starts from
 *  	8:55 - the beginning of the (nine-to-five) work day).
 *  	This time is updated every second (not shown) and is synchronized with the DbModel's class current time, 
 *  	at least until a specialist logs in.
 *  	State of the whole CustomerClient view is not retained and is not loaded from DB upon each launch,
 *  		it always starts from the blank slate.
 *  		That means program currently assumes that, upon initializing the blueprint DB, the main client will be started
 *  		and will stay functioning for the whole work day 
 * 	
 * Therefore, given more time, the focus would be to implement full state recovery and complete communication with DB
 * on every aspect (including the clock).
 * 
 * @author qqqky
 */

public class DisplayController {
	
	private static final DisplayController instance = new DisplayController(LocalDate.now());
	public static final LocalTime base = LocalTime.of(8, 55);
	//limitation: only 1 specialist client can be launched at once (queue simulation view is not retained - not implemented)
	private SpecialistClient specClient;
	private CustomerClient custClient;
	private final DbModel db;
	private LocalTime current;
	private Command com;
	private int minutesClicked;
	private int minutesPassed;
	
	

	public static void main(String[] args) {
		
		DisplayController.getInstance().launch(LocalDate.now());
	
	}
	private DisplayController(LocalDate date) {
		
		db = DbModel.create(this, date);
	}
	public static DisplayController getInstance() {
		
       return instance;
		
	}
	private void launch(LocalDate date) {
		
		custClient = new CustomerClient(this);
			
	}
	private Response login(String username, String password) {
			
		int result = db.checkCredentials(username, password);
		
		if(result == -1) {
			return ResponseBool.negative();
		}	
		else {
			launchNewSpecialistClient(result);
			return ResponseBool.positive();
		}	
	}
	private final void launchNewSpecialistClient(int specialistID) {
		specClient = new SpecialistClient(this, specialistID, db.retrieveSpecialistNameByID(specialistID));
	}
	private ResponseToRegistration registerCustomer(String name, String lastName) {
		if(!db.getCanServe()) return new ResponseToRegistration("Sorry, no more free space");
		else {
			return new ResponseToRegistration(db.registerCustomer(name, lastName));
		}
	}
	private void printTimeTable(int specNumber) {
		db.printTimeTable(specNumber);
	}
	private final Response changeCustomerStatus(String visitTime, TimeStatus newStatus, int specialistID) {
		boolean result = db.changeCustomerStatus(visitTime, newStatus, specialistID);
		
		return result? ResponseBool.positive() : ResponseBool.negative();
	}
	private final Response cancelVisit(String code, String lastName) {
		boolean result = db.findAndCancelVisitByCustomerName(code, lastName);
		
		return result? ResponseBool.positive(): ResponseBool.negative();
	}
	private final Response timeUntilVisit(String code, String lastName) {
		return db.timeLeftUntilVisit(code, lastName);
	}
	private final ResponseCustList retrieveTodaysClients (int specialistNum) {
		return db.retrieveCustomerList(specialistNum);
	}
	private void recalcBase() {
		LocalTime time = DisplayController.base;
		current = time.plusMinutes(minutesClicked).plusMinutes(minutesPassed);
	}
	
	void synchronize(LocalTime time) {
		current = time;
		db.synchronize(time);
	}
	void synchronize(CustomerClient client) {
		
		custClient = client;
	
	}
	void synchronize(SpecialistGUIController controller) {
		
		recalcBase();
		controller.setBase(current);
		specClient.setController(controller);
		
	}

	public void synchronize(CustomerGUIController controller) {
		
		minutesClicked = controller.getMinutesClicked();
		minutesPassed = controller.getMinutesPassed();
		custClient.setController(controller);
	}
	
	public Response setAndExecuteRequest(Command command, Client requester) {
		
		//check if requester id and name matches one in the DB
		if(requester instanceof SpecialistClient) {
			if(!((SpecialistClient)requester).getName().equals(db.retrieveSpecialistNameByID(((SpecialistClient)requester).getID()))) {
				return ResponseBool.negative();
			}
		}
		//if cancel request comes from customer client - specialist ID must always be -1
		if(requester instanceof CustomerClient) {
			if(command instanceof CmdMarkVisitCancelled && ((CmdMarkVisitCancelled)command).getSpecialistNum()!=-1) {
				return ResponseBool.negative();
			}
		}
			
		this.com = command;
		return this.execute();
	}
	/**
	 * Global execution method based on passed Command
	 */
	private Response execute() {
		
		Command cmd = this.com;
		if(cmd instanceof CmdRetrieveCustomerList) {
			return retrieveTodaysClients(((CmdRetrieveCustomerList) cmd).getSpecialistNum());
		
		}else if(cmd instanceof CmdMarkVisitBegan) {
			return changeCustomerStatus(((CmdMarkVisitBegan) cmd).getVisitTime(),
				((CmdMarkVisitBegan) cmd).getStatus(), ((CmdMarkVisitBegan) cmd).getSpecialistNum());
			
		}else if(cmd instanceof CmdMarkVisitEnded) {
			return changeCustomerStatus(((CmdMarkVisitEnded) cmd).getVisitTime(),
					((CmdMarkVisitEnded) cmd).getStatus(), ((CmdMarkVisitEnded) cmd).getSpecialistNum());
		
		}else if(cmd instanceof CmdMarkVisitCancelled) {
			
			//if specialist ID=-1, request came from customer client and has different parameters
			if(((CmdMarkVisitCancelled) cmd).getSpecialistNum() == -1) {
				return cancelVisit(((CmdMarkVisitCancelled) cmd).getCode(), ((CmdMarkVisitCancelled) cmd).getLastName());
			}else {
				return changeCustomerStatus(((CmdMarkVisitCancelled) cmd).getVisitTime(),
					((CmdMarkVisitCancelled) cmd).getStatus(), ((CmdMarkVisitCancelled) cmd).getSpecialistNum());
			}
			
		}else if(cmd instanceof CmdRegistrationRequest) {
			return registerCustomer(((CmdRegistrationRequest) cmd).getName(), ((CmdRegistrationRequest) cmd).getLastName());
		
		}else if(cmd instanceof CmdGetWaitingTime) {
			return timeUntilVisit(((CmdGetWaitingTime) cmd).getCode(), ((CmdGetWaitingTime) cmd).getName());
		}else if(cmd instanceof CmdLogin) {
			return login(((CmdLogin) cmd).getUsername(), ((CmdLogin) cmd).getPassword());
		}else return ResponseBool.getEmpty();

	}
	
	//helper method for the GUI to imitate 'waiting queue'
	void supplyQueuePosition(int id, String code) {
		custClient.supplyQueuePosition(id, code);
	}
	
}
