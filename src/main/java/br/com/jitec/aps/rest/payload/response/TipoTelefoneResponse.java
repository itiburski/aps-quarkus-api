package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class TipoTelefoneResponse {

	private UUID uid;
	private String descricao;

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
