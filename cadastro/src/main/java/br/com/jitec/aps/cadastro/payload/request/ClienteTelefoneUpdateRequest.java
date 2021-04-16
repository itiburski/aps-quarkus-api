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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteTelefoneUpdateRequest instance;

		private Builder() {
			instance = new ClienteTelefoneUpdateRequest();
		}

		public Builder withNumero(Integer numero) {
			instance.setNumero(numero);
			return this;
		}

		public Builder withTelefoneUid(UUID telefoneUid) {
			instance.setTelefoneUid(telefoneUid);
			return this;
		}

		public Builder withTipoTelefoneUid(UUID tipoTelefoneUid) {
			instance.setTipoTelefoneUid(tipoTelefoneUid);
			return this;
		}

		public ClienteTelefoneUpdateRequest build() {
			return instance;
		}
	}

}
