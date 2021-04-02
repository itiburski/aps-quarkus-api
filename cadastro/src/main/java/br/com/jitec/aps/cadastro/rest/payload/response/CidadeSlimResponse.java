package br.com.jitec.aps.cadastro.rest.payload.response;

import java.util.UUID;

public class CidadeSlimResponse {

	private UUID cidadeUid;
	private String nome;
	private String uf;

	public UUID getCidadeUid() {
		return cidadeUid;
	}

	public void setCidadeUid(UUID cidadeUid) {
		this.cidadeUid = cidadeUid;
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
