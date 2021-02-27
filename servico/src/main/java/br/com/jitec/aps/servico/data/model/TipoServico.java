package br.com.jitec.aps.servico.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "TIPO_SERVICO")
public class TipoServico extends APSEntity {

	@Column(name = "NOME")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoServico() {
		// default constructor
	}

	public TipoServico(String nome) {
		super();
		this.nome = nome;
	}

}
