package br.com.jitec.aps.servico.rest.payload.response;

import java.util.UUID;

public class TipoBaixaResponse {

	private UUID tipoBaixaUid;
	private String nome;
	private Integer version;

	public UUID getTipoBaixaUid() {
		return tipoBaixaUid;
	}

	public void setTipoBaixaUid(UUID tipoBaixaUid) {
		this.tipoBaixaUid = tipoBaixaUid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
