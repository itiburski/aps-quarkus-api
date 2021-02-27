package br.com.jitec.aps.servico.rest.payload.response;

import java.util.UUID;

public class TipoServicoResponse {

	private UUID tipoServicoUid;
	private String nome;
	private Integer version;

	public UUID getTipoServicoUid() {
		return tipoServicoUid;
	}

	public void setTipoServicoUid(UUID tipoServicoUid) {
		this.tipoServicoUid = tipoServicoUid;
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
