package br.com.jitec.aps.cadastro.rest.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TipoTelefoneRequest {

	@NotBlank
	@Size(max = 40)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
