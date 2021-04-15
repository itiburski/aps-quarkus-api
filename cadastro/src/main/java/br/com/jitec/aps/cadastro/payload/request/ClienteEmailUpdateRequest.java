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

	@Override
	public String toString() {
		return "ClienteEmailUpdateRequest [email=" + email + ", emailUid=" + emailUid + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteEmailUpdateRequest instance;

		private Builder() {
			instance = new ClienteEmailUpdateRequest();
		}

		public Builder withEmailUid(UUID emailUid) {
			instance.setEmailUid(emailUid);
			return this;
		}

		public Builder withEmail(String email) {
			instance.setEmail(email);
			return this;
		}

		public ClienteEmailUpdateRequest build() {
			return instance;
		}
	}
}
