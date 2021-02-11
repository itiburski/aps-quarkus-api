package br.com.jitec.aps.cadastro.rest.payload.response;

public class ViolationResponse {

	private String field;

	private String message;

	public ViolationResponse() {
		// default constructor
	}

	public ViolationResponse(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
