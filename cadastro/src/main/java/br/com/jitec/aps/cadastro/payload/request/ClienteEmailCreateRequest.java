package br.com.jitec.aps.cadastro.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ClienteEmailCreateRequest {

	@NotBlank
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ClienteEmailCreateRequest [email=" + email + "]";
	}

}
