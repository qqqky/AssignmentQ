package dto;

import app.DbModel.TimeStatus;
import display.SpecialistClient;

public class CmdMarkVisitEnded implements Command {
	private int specialistNum;
	private String visitTime;
	private final TimeStatus status = TimeStatus.SERVICED;
		
	public CmdMarkVisitEnded(String visitTime, int specialistNum) {
		this.specialistNum = specialistNum;
		this.visitTime = visitTime;
	}
	public CmdMarkVisitEnded(SpecialistClient client) {
		this.specialistNum = client.getID();
	}
		
	public int getSpecialistNum() {
		return specialistNum;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public TimeStatus getStatus() {
		return status;
		}
	
}
