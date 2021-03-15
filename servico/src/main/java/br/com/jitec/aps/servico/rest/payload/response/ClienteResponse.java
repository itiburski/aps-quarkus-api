package br.com.jitec.aps.servico.rest.payload.response;

import java.util.UUID;

public class ClienteResponse {

	private UUID clienteUid;
	private String nome;

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
