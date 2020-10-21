package br.com.jitec.aps.rest.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.jitec.aps.rest.validation.ValidationMessages;

public class CidadeRequest {

	@NotBlank(message = ValidationMessages.NOME_NOT_BLANK)
	@Size(max = 60, message = ValidationMessages.NOME_SIZE)
	private String nome;

	@NotBlank(message = ValidationMessages.UF_NOT_BLANK)
	@Size(min = 2, max = 2, message = ValidationMessages.UF_SIZE)
	private String uf;

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
