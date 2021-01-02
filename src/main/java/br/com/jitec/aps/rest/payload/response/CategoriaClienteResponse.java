package br.com.jitec.aps.rest.payload.response;

import java.util.UUID;

public class CategoriaClienteResponse {

	private UUID categoriaClienteUid;
	private String descricao;
	private Integer version;

	public UUID getCategoriaClienteUid() {
		return categoriaClienteUid;
	}

	public void setCategoriaClienteUid(UUID categoriaClienteUid) {
		this.categoriaClienteUid = categoriaClienteUid;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
