package br.com.jitec.aps.servico.data.model.builder;

import java.util.UUID;

import br.com.jitec.aps.servico.data.model.TipoBaixa;

public class TipoBaixaBuilder {

	private TipoBaixa instance;

	public TipoBaixaBuilder() {
		instance = new TipoBaixa();
	}

	public static TipoBaixaBuilder create() {
		return new TipoBaixaBuilder();
	}

	public TipoBaixaBuilder withUid(UUID uid) {
		instance.setUid(uid);
		return this;
	}

	public TipoBaixaBuilder withNome(String nome) {
		instance.setNome(nome);
		return this;
	}

	public TipoBaixaBuilder withDefaultValues() {
		return this.withUid(UUID.fromString("a72b079f-2e7c-438d-b7c0-1923f7ae9360")).withNome("nome");
	}

	public TipoBaixa build() {
		return instance;
	}
}
