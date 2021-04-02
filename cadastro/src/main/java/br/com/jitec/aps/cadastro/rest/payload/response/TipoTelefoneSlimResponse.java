package br.com.jitec.aps.cadastro.rest.payload.response;

import java.util.UUID;

public class TipoTelefoneSlimResponse {

	private UUID tipoTelefoneUid;
	private String descricao;

	public UUID getTipoTelefoneUid() {
		return tipoTelefoneUid;
	}

	public void setTipoTelefoneUid(UUID tipoTelefoneUid) {
		this.tipoTelefoneUid = tipoTelefoneUid;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
