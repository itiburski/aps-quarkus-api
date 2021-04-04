package br.com.jitec.aps.cadastro.payload.request;

import javax.validation.constraints.NotBlank;

public class CategoriaClienteRequest {

	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
