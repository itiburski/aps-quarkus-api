package br.com.jitec.aps.cadastro.payload.response;

import java.util.UUID;

public class ClienteTelefoneResponse {

	private UUID telefoneUid;
	private TipoTelefoneSlimResponse tipoTelefone;
	private Integer numero;

	public UUID getTelefoneUid() {
		return telefoneUid;
	}

	public void setTelefoneUid(UUID telefoneUid) {
		this.telefoneUid = telefoneUid;
	}

	public TipoTelefoneSlimResponse getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefoneSlimResponse tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
