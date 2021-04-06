package dto;

import java.util.ArrayList;
import java.util.List;

/*
 * Class is used as response to queries about today's customers
 */

public class ResponseCustList implements Response {

	private List<Customer> customers = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void add(Customer cust) {
		customers.add(cust);
	}
	public void remove(int index) {
		customers.remove(index);
	}
	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		System.out.println("Size of list: "+customers.size());
		for(Customer item: customers) {
			sb.append(item).append(newLine);
		}
		return sb.toString();
	}
	public List<Customer> getList(){
		return customers;
	}

}
