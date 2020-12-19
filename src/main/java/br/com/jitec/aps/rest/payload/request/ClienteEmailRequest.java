package br.com.jitec.aps.rest.payload.request;

import java.util.UUID;

import javax.validation.constraints.Email;

public class ClienteEmailRequest {

	private UUID uid;

	@Email
	private String email;

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
