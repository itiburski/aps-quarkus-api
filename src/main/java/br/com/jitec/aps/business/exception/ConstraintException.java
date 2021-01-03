package br.com.jitec.aps.business.exception;

public class ConstraintException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ConstraintException() {
		super();
	}

	public ConstraintException(String message) {
		super(message);
	}
}
