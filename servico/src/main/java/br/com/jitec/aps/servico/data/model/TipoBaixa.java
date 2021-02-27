package br.com.jitec.aps.servico.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "TIPO_BAIXA")
public class TipoBaixa extends APSEntity {

	@Column(name = "NOME")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoBaixa() {
		// default constructor
	}

	public TipoBaixa(String nome) {
		super();
		this.nome = nome;
	}

}
