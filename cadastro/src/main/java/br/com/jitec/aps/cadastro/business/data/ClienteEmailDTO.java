package br.com.jitec.aps.cadastro.business.data;

import java.util.UUID;

public class ClienteEmailDTO {

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

	@Override
	public String toString() {
		return "ClienteEmailDTO [emailUid=" + emailUid + ", email=" + email + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteEmailDTO instance;

		private Builder() {
			instance = new ClienteEmailDTO();
		}

		public Builder withEmail(String email) {
			instance.setEmail(email);
			return this;
		}

		public Builder withEmailUid(UUID emailUid) {
			instance.setEmailUid(emailUid);
			return this;
		}

		public ClienteEmailDTO build() {
			return instance;
		}
	}

}
