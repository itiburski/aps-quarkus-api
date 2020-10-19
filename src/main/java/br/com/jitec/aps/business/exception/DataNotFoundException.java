package br.com.jitec.aps.business.exception;

public class DataNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message) {
		super(message);
	}
}
