package br.com.jitec.aps.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIA_CLIENTE")
public class CategoriaCliente extends APSEntity {

	@Column(name = "DESCRICAO")
	public String descricao;

	public CategoriaCliente() {
		// default constructor
	}

	public CategoriaCliente(String descricao) {
		super();
		this.descricao = descricao;
	}

}
