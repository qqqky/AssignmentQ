package display;

import java.time.LocalTime;

import app.DisplayController;
import dto.Client;
import dto.CmdMarkVisitBegan;
import dto.CmdMarkVisitCancelled;
import dto.CmdMarkVisitEnded;
import dto.CmdRetrieveCustomerList;
import dto.Response;
import dto.ResponseBool;
import dto.ResponseCustList;


public class SpecialistClient implements Client {
	
	private DisplayController control;
	private final int specialistID;
	private final String username;
	private ResponseCustList todaysList;
	private SpecialistGUIController gui;
	private LocalTime current;

	public static void main(String[] args) {
		
	}
	public SpecialistClient(DisplayController control, int specialistID, String username) {
		this.username = username;
		this.control = control;
		this.specialistID = specialistID;
		
	}
	
	//gets current customer list (can only get his own)
	public ResponseCustList getCurrentList() { 
		return(ResponseCustList)control.setAndExecuteRequest(new CmdRetrieveCustomerList(specialistID), this);	
	}
	public boolean markVisitBegan(String time, int specID) {
		Response response = control.setAndExecuteRequest(new CmdMarkVisitBegan(time, specialistID), this);
		return ((ResponseBool)response).getValue();
	}
	public boolean markVisitEnded(String time, int specID) {
		Response response = control.setAndExecuteRequest(new CmdMarkVisitEnded(time, specialistID), this);
		return ((ResponseBool)response).getValue();
	}
	public boolean markVisitCancelled(String time, int specID) {
		Response response = control.setAndExecuteRequest(new CmdMarkVisitCancelled(time, specialistID), this);
		return ((ResponseBool)response).getValue();
	}
	public int getID() {
		return specialistID;
	}
	public String getName() {
		return username;
	}
	void updateCustomerList() {
		todaysList = this.getCurrentList();

	}
	void printCustomerList() {
		updateCustomerList();
		
	}

	public void setController(SpecialistGUIController controller) {
		
		this.gui = controller;
		gui.setSpecialistClient(this);
		gui.setSpecialistID(this.getID());
		
	}
	public void synchronize(CustomerGUIController controller) {

		control.synchronize(controller);
	}
	void synchronize(SpecialistGUIController controller) {
		current = controller.getTime();
	}
	
}
