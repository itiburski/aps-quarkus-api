package br.com.jitec.aps.cadastro.business.exception;

public class InvalidDataException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String message) {
		super(message);
	}

}