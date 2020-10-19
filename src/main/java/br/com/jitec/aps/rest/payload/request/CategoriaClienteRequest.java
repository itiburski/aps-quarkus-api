package br.com.jitec.aps.rest.payload.request;

import javax.validation.constraints.NotBlank;

import br.com.jitec.aps.rest.validation.ValidationMessages;

public class CategoriaClienteRequest {

	@NotBlank(message = ValidationMessages.DESCRICAO_NOT_BLANK)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
