package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class ClienteTelefoneResponse {

	private UUID telefoneUid;
	private TipoTelefoneResponse tipoTelefone;
	private Integer numero;

	public UUID getTelefoneUid() {
		return telefoneUid;
	}

	public void setTelefoneUid(UUID telefoneUid) {
		this.telefoneUid = telefoneUid;
	}

	public TipoTelefoneResponse getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefoneResponse tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
