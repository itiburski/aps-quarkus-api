package br.com.jitec.aps.servico.rest.payload.response;

import java.util.UUID;

public class TipoBaixaSlimResponse {

	private UUID tipoBaixaUid;
	private String nome;

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

}
