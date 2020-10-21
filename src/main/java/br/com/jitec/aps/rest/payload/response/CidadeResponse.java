package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class CidadeResponse {

	private UUID uid;
	private String nome;
	private String uf;

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
