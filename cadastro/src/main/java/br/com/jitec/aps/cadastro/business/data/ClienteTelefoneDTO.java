package br.com.jitec.aps.cadastro.business.data;

import java.util.UUID;

public class ClienteTelefoneDTO {

	private UUID telefoneUid;

	private UUID tipoTelefoneUid;

	private Integer numero;

	public UUID getTipoTelefoneUid() {
		return tipoTelefoneUid;
	}

	public void setTipoTelefoneUid(UUID tipoTelefoneUid) {
		this.tipoTelefoneUid = tipoTelefoneUid;
	}

	public UUID getTelefoneUid() {
		return telefoneUid;
	}

	public void setTelefoneUid(UUID telefoneUid) {
		this.telefoneUid = telefoneUid;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "ClienteTelefoneDTO [telefoneUid=" + telefoneUid + ", tipoTelefoneUid=" + tipoTelefoneUid + ", numero="
				+ numero + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteTelefoneDTO instance;

		private Builder() {
			instance = new ClienteTelefoneDTO();
		}

		public Builder withNumero(Integer numero) {
			instance.setNumero(numero);
			return this;
		}

		public Builder withTipoTelefoneUid(UUID tipoTelefoneUid) {
			instance.setTipoTelefoneUid(tipoTelefoneUid);
			return this;
		}

		public Builder withTelefoneUid(UUID telefoneUid) {
			instance.setTelefoneUid(telefoneUid);
			return this;
		}

		public ClienteTelefoneDTO build() {
			return instance;
		}
	}

}
