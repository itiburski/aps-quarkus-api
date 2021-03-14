package br.com.jitec.aps.commons.business.data;

import java.util.UUID;

public class ClienteResumidoDto {

	private UUID uid;
	private String nome;
	private Boolean ativo;

	public ClienteResumidoDto() {
		// default constructor
	}

	public ClienteResumidoDto(UUID uid, String nome, Boolean ativo) {
		super();
		this.uid = uid;
		this.nome = nome;
		this.ativo = ativo;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "[uid=" + uid + ", nome=" + nome + ", ativo=" + ativo + "]";
	}

}
