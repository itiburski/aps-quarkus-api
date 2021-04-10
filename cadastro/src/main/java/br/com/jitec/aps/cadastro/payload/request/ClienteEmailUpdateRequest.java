package br.com.jitec.aps.cadastro.payload.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ClienteEmailUpdateRequest {

	private UUID emailUid;

	@NotBlank
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
