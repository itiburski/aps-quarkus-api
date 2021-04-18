package br.com.jitec.aps.cadastro.payload.request.builder;

import java.util.UUID;

import br.com.jitec.aps.cadastro.payload.request.ClienteEmailUpdateRequest;

public class ClienteEmailUpdateRequestBuilder {

	private ClienteEmailUpdateRequest instance;

	private ClienteEmailUpdateRequestBuilder() {
		instance = new ClienteEmailUpdateRequest();
	}

	public static ClienteEmailUpdateRequestBuilder create() {
		return new ClienteEmailUpdateRequestBuilder();
	}

	public ClienteEmailUpdateRequestBuilder withEmailUid(UUID emailUid) {
		instance.setEmailUid(emailUid);
		return this;
	}

	public ClienteEmailUpdateRequestBuilder withEmail(String email) {
		instance.setEmail(email);
		return this;
	}

	public ClienteEmailUpdateRequest build() {
		return instance;
	}
}
