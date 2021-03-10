package br.com.jitec.aps.servico.data.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "CLIENTE_REPLICA")
public class ClienteReplica extends APSEntity {

	public ClienteReplica() {
		super();
		// UUID deve ser setado manualmente na rotina de sincronização
		setUid(null);
	}

	public ClienteReplica(UUID uid, Boolean ativo) {
		super();
		this.ativo = ativo;
		setUid(uid);
	}

	@Column(name = "ATIVO")
	private Boolean ativo;

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
