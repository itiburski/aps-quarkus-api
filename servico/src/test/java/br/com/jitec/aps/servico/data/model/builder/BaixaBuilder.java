package br.com.jitec.aps.servico.data.model.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.TipoBaixa;

public class BaixaBuilder {

	private Baixa instance;

	public BaixaBuilder() {
		instance = new Baixa();
	}

	public static BaixaBuilder create() {
		return new BaixaBuilder();
	}

	public BaixaBuilder withUid(UUID uid) {
		instance.setUid(uid);
		return this;
	}

	public BaixaBuilder withClienteReplica(ClienteReplica cliente) {
		instance.setCliente(cliente);
		return this;
	}

	public BaixaBuilder withTipoBaixa(TipoBaixa tipoBaixa) {
		instance.setTipoBaixa(tipoBaixa);
		return this;
	}

	public BaixaBuilder withData(OffsetDateTime data) {
		instance.setData(data);
		return this;
	}

	public BaixaBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public BaixaBuilder withObservacao(String observacao) {
		instance.setObservacao(observacao);
		return this;
	}

	public BaixaBuilder withDefaultValues() {
		ClienteReplica cliente = ClienteReplicaBuilder.create().withDefaultValues().build();
		TipoBaixa tipoBaixa = TipoBaixaBuilder.create().withDefaultValues().build();

		return this.withUid(UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f")).withClienteReplica(cliente)
				.withTipoBaixa(tipoBaixa).withData(OffsetDateTime.now()).withValor(BigDecimal.TEN)
				.withObservacao("observacao");
	}

	public Baixa build() {
		return instance;
	}

}
