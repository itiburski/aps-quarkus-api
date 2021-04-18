package br.com.jitec.aps.cadastro.payload.request.builder;

import br.com.jitec.aps.cadastro.payload.request.ClienteEmailCreateRequest;

public class ClienteEmailCreateRequestBuilder {

	private ClienteEmailCreateRequest instance;

	private ClienteEmailCreateRequestBuilder() {
		instance = new ClienteEmailCreateRequest();
	}

	public static ClienteEmailCreateRequestBuilder create() {
		return new ClienteEmailCreateRequestBuilder();
	}

	public ClienteEmailCreateRequestBuilder withEmail(String email) {
		instance.setEmail(email);
		return this;
	}

	public ClienteEmailCreateRequest build() {
		return instance;
	}
}
