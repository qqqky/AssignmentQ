package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Customer {
	
	LocalTime time;
	String name;
	String lastName;
	String code;
	String status;
	LocalDate date;

	public Customer(LocalTime time, String name, String lastName, String code, String status, LocalDate date) {
		
		this.time = time;
		this.name = name;
		this.lastName = lastName;
		this.code = code;
		this.status = status;
		this.date = date;
	}
	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Time: ").append(time.toString()).append(" Name: ").append(name)
		.append(" Last_name: ").append(lastName).append(" Code: ").append(code)
		.append(" Status: ").append(status).append(" Date: ").append(date.toString());
		
		return sb.toString();
	}
	public LocalTime getTime() {
		return time;
	}
	public String getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
}
