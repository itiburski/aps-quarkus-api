package br.com.jitec.aps.cadastro.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CidadeRequest {

	@NotBlank
	@Size(max = 60)
	private String nome;

	@NotBlank
	@Size(min = 2, max = 2)
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
