package app;


import java.io.IOException;
import java.time.LocalTime;

import app.DbModel.ObjectStatus;
import display.CustomerGUIController;
import display.SpecialistGUIController;
import dto.Client;
import dto.CmdGetWaitingTime;
import dto.CmdLogin;
import dto.CmdMarkVisitCancelled;
import dto.CmdRegistrationRequest;
import dto.Response;
import dto.ResponseBool;
import dto.ResponseToRegistration;
import dto.ResponseWaitTime;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerClient extends Application implements Client {

	private DisplayController control;
	private final ObjectStatus status = ObjectStatus.CUSTOMER;
	private int registrationCount = 0;
	private int minutesClicked = 0;
	private int minutesPassed;
	private CustomerGUIController gui;
	private LocalTime current;
	private boolean initialCustSync = false;
	

	public static void main(String[] args) {
		
	}
	public CustomerClient(DisplayController control) {
		this.control = control;
		launch();
	}
	/*
	 * FXML requires no-arg constructor
	 */
	public CustomerClient() {
		
	}
	
	/**
	 * Registers a customer for a closest visit time (if more than one identical time is available, will choose the 
	 * specialist randomly).
	 * @return String - reservation code (0-99) or "Sorry, no more free space left for today"
	 */
	public String register(String name, String lastName) {
		
		Response response = control.setAndExecuteRequest(new CmdRegistrationRequest(name, lastName), this);
		registrationCount++;
		return ((ResponseToRegistration)response).getCode();
	}
	
	/**
	 * Cancels an existing visit, as long as its status is "RESERVED".
	 * Customers must provide their last name along with reservation token.
	 * @param code - reservation code
	 */
	public boolean markVisitCancelled(String code, String lastName) {
		Response response = control.setAndExecuteRequest(new CmdMarkVisitCancelled(code, lastName), this);
		return ((ResponseBool)response).getValue();
	}
	/**
	 * This assumes there will be no duplicate codes that also share RESERVED status 
	 */
	public String getWaitingTime(String code, String lastName) {
		Response response = control.setAndExecuteRequest(new CmdGetWaitingTime(code, lastName), this);
		return ((ResponseWaitTime)response).getWaitTime();
		}
	
	public int getRegCount() {
		
		return registrationCount;
	}
	public void supplyQueuePosition(int position, String code) {
		gui.insertCustomerToGUI(position, code);
	}

@Override 
	public void start(Stage stage) throws IOException {
		
	//node: at this point new a CustomerClient is initialized with no-arg constructor and all references dereferenced
	
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/display/CustomerGUI.fxml"));
		Parent root = loader.load();
		this.gui = loader.getController();
		gui.setCustomerClient(this);
		
		//attach handle
		gui.setController(DisplayController.getInstance());
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
/**
 * Asks displayController to launch the specialist GUI
 */
	public boolean authenticate(String username, String password) {
		Response response = control.setAndExecuteRequest(new CmdLogin(username, password), this);
		return ((ResponseBool)response).getValue();
	}
	public void setController(DisplayController controller) {
		this.control = controller;
	}
	public void setController(CustomerGUIController controller) {
		this.gui = controller;
		if(gui !=null) {
			gui.setCustomerClient(this);
		}
	}
	public CustomerClient getCurrent() {
		return this;
	}
	public void synchronize(CustomerGUIController controller) {
		current = controller.getCurrent();
		minutesClicked = controller.getMinutesClicked();
		minutesPassed = controller.getMinutesPassed();
		if(!initialCustSync) {
			control.synchronize(this);	//on first launch we want to reinitialize the reference
			control.synchronize(controller.getCurrent());
			initialCustSync = true;
		}else {
			control.synchronize(controller.getCurrent());
		}	
	}
	public void synchronize(SpecialistGUIController controller) {
		
		control.synchronize(controller);
	}
	public LocalTime getClientTime() {
		return current;
	}
	public int getMinutesPassed() {
		return minutesPassed;
	}
	public int getMinutesClicked() {
		return minutesClicked;
	}
	
	
}
