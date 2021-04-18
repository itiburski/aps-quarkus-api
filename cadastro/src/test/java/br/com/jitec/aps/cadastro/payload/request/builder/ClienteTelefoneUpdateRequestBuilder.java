package br.com.jitec.aps.cadastro.payload.request.builder;

import java.util.UUID;

import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneUpdateRequest;

public class ClienteTelefoneUpdateRequestBuilder {

	private ClienteTelefoneUpdateRequest instance;

	private ClienteTelefoneUpdateRequestBuilder() {
		instance = new ClienteTelefoneUpdateRequest();
	}

	public static ClienteTelefoneUpdateRequestBuilder create() {
		return new ClienteTelefoneUpdateRequestBuilder();
	}

	public ClienteTelefoneUpdateRequestBuilder withNumero(Integer numero) {
		instance.setNumero(numero);
		return this;
	}

	public ClienteTelefoneUpdateRequestBuilder withTelefoneUid(UUID telefoneUid) {
		instance.setTelefoneUid(telefoneUid);
		return this;
	}

	public ClienteTelefoneUpdateRequestBuilder withTipoTelefoneUid(UUID tipoTelefoneUid) {
		instance.setTipoTelefoneUid(tipoTelefoneUid);
		return this;
	}

	public ClienteTelefoneUpdateRequest build() {
		return instance;
	}

}
