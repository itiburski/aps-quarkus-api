package br.com.jitec.aps.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CIDADE")
public class Cidade extends APSEntity {

	@Column(name = "NOME")
	public String nome;

	@Column(name = "UF")
	public String uf;

	public Cidade() {
		// default constructor
	}

	public Cidade(String nome, String uf) {
		super();
		this.nome = nome;
		this.uf = uf;
	}

}
