package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class ClienteEmailResponse {

	private UUID uid;
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
