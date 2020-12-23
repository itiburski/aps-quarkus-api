package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class ClienteTelefoneResponse {

	private UUID uid;
	private TipoTelefoneResponse tipoTelefone;
	private Integer numero;

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
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
