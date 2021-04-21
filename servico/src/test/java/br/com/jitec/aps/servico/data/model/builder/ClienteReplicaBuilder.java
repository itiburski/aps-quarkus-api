package br.com.jitec.aps.servico.data.model.builder;

import java.util.UUID;

import br.com.jitec.aps.servico.data.model.ClienteReplica;

public class ClienteReplicaBuilder {

	private ClienteReplica instance;

	public ClienteReplicaBuilder() {
		instance = new ClienteReplica();
	}

	public static ClienteReplicaBuilder create() {
		return new ClienteReplicaBuilder();
	}

	public ClienteReplicaBuilder withUid(UUID uid) {
		instance.setUid(uid);
		return this;
	}

	public ClienteReplicaBuilder withNome(String nome) {
		instance.setNome(nome);
		return this;
	}

	public ClienteReplicaBuilder withAtivo(Boolean ativo) {
		instance.setAtivo(ativo);
		return this;
	}

	public ClienteReplicaBuilder withDefaultValues() {
		return this.withUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b")).withNome("nome")
				.withAtivo(Boolean.TRUE);
	}

	public ClienteReplica build() {
		return instance;
	}
}
