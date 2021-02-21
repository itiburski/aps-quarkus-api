package br.com.jitec.aps.commons.rest.http;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public enum AdditionalStatus implements StatusType {

	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

	private final int code;
	private final String reason;
	private final Family family;

	AdditionalStatus(final int statusCode, final String reasonPhrase) {
		this.code = statusCode;
		this.reason = reasonPhrase;
		this.family = Response.Status.Family.familyOf(statusCode);
	}

	@Override
	public int getStatusCode() {
		return code;
	}

	@Override
	public Family getFamily() {
		return family;
	}

	@Override
	public String getReasonPhrase() {
		return reason;
	}

}
