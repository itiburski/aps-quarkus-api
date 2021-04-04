package br.com.jitec.aps.servico.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class BaixaCreateRequest extends GenericBaixaRequest {

	@NotNull
	private UUID clienteUid;

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

}
