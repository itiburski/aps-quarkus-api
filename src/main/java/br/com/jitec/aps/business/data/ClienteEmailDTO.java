package br.com.jitec.aps.business.data;

import java.util.UUID;

public class ClienteEmailDTO {

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

	@Override
	public String toString() {
		return "ClienteEmailDTO [uid=" + uid + ", email=" + email + "]";
	}

	public static class Builder {
		private ClienteEmailDTO instance;

		public Builder() {
			instance = new ClienteEmailDTO();
		}

		public Builder withEmail(String email) {
			instance.setEmail(email);
			return this;
		}

		public Builder withUid(UUID uid) {
			instance.setUid(uid);
			return this;
		}

		public ClienteEmailDTO build() {
			return instance;
		}
	}

}
