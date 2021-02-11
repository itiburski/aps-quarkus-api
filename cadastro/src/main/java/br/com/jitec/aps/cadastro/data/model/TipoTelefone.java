package br.com.jitec.aps.cadastro.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_FONE")
public class TipoTelefone extends APSEntity {

	@Column(name = "DESCRICAO")
	private String descricao;

	public TipoTelefone() {
		// default constructor
	}

	public TipoTelefone(String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "TipoTelefone [descricao=" + descricao + ", id=" + getId() + ", UUID=" + getUid() + "]";
	}

}
