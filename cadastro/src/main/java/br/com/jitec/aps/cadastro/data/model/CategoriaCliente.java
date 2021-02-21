package br.com.jitec.aps.cadastro.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "CATEGORIA_CLIENTE")
public class CategoriaCliente extends APSEntity {

	@Column(name = "DESCRICAO")
	private String descricao;

	public CategoriaCliente() {
		// default constructor
	}

	public CategoriaCliente(String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
