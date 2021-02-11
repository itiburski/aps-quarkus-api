package br.com.jitec.aps.cadastro.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CIDADE")
public class Cidade extends APSEntity {

	@Column(name = "NOME")
	private String nome;

	@Column(name = "UF")
	private String uf;

	public Cidade() {
		// default constructor
	}

	public Cidade(String nome, String uf) {
		super();
		this.nome = nome;
		this.uf = uf;
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

}
