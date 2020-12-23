package br.com.jitec.aps.business.data;

import java.util.UUID;

public class ClienteTelefoneDTO {

	private UUID uid;

	private UUID uidTipoTelefone;

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

	@Override
	public String toString() {
		return "ClienteTelefoneDTO [uid=" + uid + ", uidTipoTelefone=" + uidTipoTelefone + ", numero=" + numero + "]";
	}

	public static class Builder {
		private ClienteTelefoneDTO instance;

		public Builder() {
			instance = new ClienteTelefoneDTO();
		}

		public Builder withNumero(Integer numero) {
			instance.setNumero(numero);
			return this;
		}

		public Builder withUidTipoTelefone(UUID uidTipoTelefone) {
			instance.setUidTipoTelefone(uidTipoTelefone);
			return this;
		}

		public Builder withUid(UUID uid) {
			instance.setUid(uid);
			return this;
		}

		public ClienteTelefoneDTO build() {
			return instance;
		}
	}

}
