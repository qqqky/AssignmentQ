package dto;

public class ResponseBool implements Response{

	private boolean responseValue;
	
	private ResponseBool(boolean value) {
		this.responseValue = value;
	}
	private ResponseBool() {
		
	}
	public static Response getEmpty() {
		return new ResponseBool();
	}
	public static Response negative() {
		return new ResponseBool(false);
	}
	public static Response positive() {
		return new ResponseBool(true);
	}
	public boolean getValue() {
		return responseValue;
	}
}
