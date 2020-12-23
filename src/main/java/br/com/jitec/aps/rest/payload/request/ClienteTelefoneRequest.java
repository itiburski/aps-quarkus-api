package br.com.jitec.aps.rest.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class ClienteTelefoneRequest {

	private UUID uid;

	private UUID uidTipoTelefone;

	@NotBlank
	private Integer numero;

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public UUID getUidTipoTelefone() {
		return uidTipoTelefone;
	}

	public void setUidTipoTelefone(UUID uidTipoTelefone) {
		this.uidTipoTelefone = uidTipoTelefone;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
