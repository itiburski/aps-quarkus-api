package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class ClienteEmailResponse {

	private UUID emailUid;
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
