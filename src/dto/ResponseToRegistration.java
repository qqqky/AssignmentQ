package dto;

public class ResponseToRegistration implements Response{

	private final String responseCode;
	
	public ResponseToRegistration(String code) {
		this.responseCode = code;
	}
	public String getCode() {
		return responseCode;
	}
}
