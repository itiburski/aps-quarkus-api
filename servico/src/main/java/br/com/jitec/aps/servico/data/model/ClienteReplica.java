package br.com.jitec.aps.servico.data.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "CLIENTE_REPLICA")
public class ClienteReplica extends APSEntity {

	@Column(name = "NOME")
	private String nome;

	@Column(name = "ATIVO")
	private Boolean ativo;

	public ClienteReplica() {
		super();
		// UUID deve ser setado manualmente na rotina de sincronização
		setUid(null);
	}

	public ClienteReplica(UUID uid, String nome, Boolean ativo) {
		super();
		this.nome = nome;
		this.ativo = ativo;
		setUid(uid);
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
