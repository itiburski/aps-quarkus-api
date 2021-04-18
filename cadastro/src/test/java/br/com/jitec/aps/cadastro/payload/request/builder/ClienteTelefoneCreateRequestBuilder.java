package br.com.jitec.aps.cadastro.payload.request.builder;

import java.util.UUID;

import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneCreateRequest;

public class ClienteTelefoneCreateRequestBuilder {

	private ClienteTelefoneCreateRequest instance;

	private ClienteTelefoneCreateRequestBuilder() {
		instance = new ClienteTelefoneCreateRequest();
	}

	public static ClienteTelefoneCreateRequestBuilder create() {
		return new ClienteTelefoneCreateRequestBuilder();
	}

	public ClienteTelefoneCreateRequestBuilder withNumero(Integer numero) {
		instance.setNumero(numero);
		return this;
	}

	public ClienteTelefoneCreateRequestBuilder withTipoTelefoneUid(UUID tipoTelefoneUid) {
		instance.setTipoTelefoneUid(tipoTelefoneUid);
		return this;
	}

	public ClienteTelefoneCreateRequest build() {
		return instance;
	}
}
