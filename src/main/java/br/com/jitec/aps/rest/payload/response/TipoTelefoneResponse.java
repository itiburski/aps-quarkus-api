package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class TipoTelefoneResponse {

	private UUID tipoTelefoneUid;
	private String descricao;
	private Integer version;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
