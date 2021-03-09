package br.com.jitec.aps.commons.business.dto;

import java.util.UUID;

public class ClienteResumidoDTO {

	private UUID uid;
	private Boolean ativo;

	public ClienteResumidoDTO() {
		// default constructor
	}

	public ClienteResumidoDTO(UUID uid, Boolean ativo) {
		super();
		this.uid = uid;
		this.ativo = ativo;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
