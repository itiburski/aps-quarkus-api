package br.com.jitec.aps.rest.payload.request;

import java.util.UUID;

import javax.validation.constraints.Email;

public class ClienteEmailRequest {

	private UUID emailUid;

	@Email
	private String email;

	public UUID getEmailUid() {
		return emailUid;
	}

	public void setEmailUid(UUID emailUid) {
		this.emailUid = emailUid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
