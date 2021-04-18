package br.com.jitec.aps.cadastro.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class ClienteTelefoneUpdateRequest {

	private UUID telefoneUid;
	private UUID tipoTelefoneUid;

	@NotNull
	private Integer numero;

	public UUID getTelefoneUid() {
		return telefoneUid;
	}

	public void setTelefoneUid(UUID telefoneUid) {
		this.telefoneUid = telefoneUid;
	}

	public UUID getTipoTelefoneUid() {
		return tipoTelefoneUid;
	}

	public void setTipoTelefoneUid(UUID tipoTelefoneUid) {
		this.tipoTelefoneUid = tipoTelefoneUid;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "ClienteTelefoneUpdateRequest [numero=" + numero + ", tipoTelefoneUid=" + tipoTelefoneUid
				+ ", telefoneUid=" + telefoneUid + "]";
	}

}
