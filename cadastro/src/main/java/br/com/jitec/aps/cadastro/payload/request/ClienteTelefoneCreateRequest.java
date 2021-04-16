package br.com.jitec.aps.cadastro.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class ClienteTelefoneCreateRequest {

	private UUID tipoTelefoneUid;

	@NotNull
	private Integer numero;

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
		return "ClienteTelefoneCreateRequest [numero=" + numero + ", tipoTelefoneUid=" + tipoTelefoneUid + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteTelefoneCreateRequest instance;

		private Builder() {
			instance = new ClienteTelefoneCreateRequest();
		}

		public Builder withNumero(Integer numero) {
			instance.setNumero(numero);
			return this;
		}

		public Builder withTipoTelefoneUid(UUID tipoTelefoneUid) {
			instance.setTipoTelefoneUid(tipoTelefoneUid);
			return this;
		}

		public ClienteTelefoneCreateRequest build() {
			return instance;
		}
	}

}
