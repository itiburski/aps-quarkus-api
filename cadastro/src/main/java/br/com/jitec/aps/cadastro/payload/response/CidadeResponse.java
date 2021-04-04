package br.com.jitec.aps.cadastro.payload.response;

import java.util.UUID;

public class CidadeResponse {

	private UUID cidadeUid;
	private String nome;
	private String uf;
	private Integer version;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
