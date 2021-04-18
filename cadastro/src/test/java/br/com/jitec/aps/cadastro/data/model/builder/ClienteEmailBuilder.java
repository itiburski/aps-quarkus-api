package br.com.jitec.aps.cadastro.data.model.builder;

import java.util.UUID;

import br.com.jitec.aps.cadastro.data.model.ClienteEmail;

public class ClienteEmailBuilder {

	private ClienteEmail instance;

	private ClienteEmailBuilder() {
		instance = new ClienteEmail();
	}

	public static ClienteEmailBuilder create() {
		return new ClienteEmailBuilder();
	}

	public ClienteEmailBuilder withEmail(String email) {
		instance.setEmail(email);
		return this;
	}

	public ClienteEmailBuilder withUid(UUID uid) {
		instance.setUid(uid);
		return this;
	}

	public ClienteEmail build() {
		return instance;
	}

}
