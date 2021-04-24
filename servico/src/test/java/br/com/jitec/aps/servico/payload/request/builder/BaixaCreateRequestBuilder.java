package br.com.jitec.aps.servico.payload.request.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.payload.request.BaixaCreateRequest;

public class BaixaCreateRequestBuilder {

	private BaixaCreateRequest instance;

	private BaixaCreateRequestBuilder() {
		instance = new BaixaCreateRequest();
	}

	public static BaixaCreateRequestBuilder create() {
		return new BaixaCreateRequestBuilder();
	}

	public BaixaCreateRequestBuilder withClienteUid(UUID clienteUid) {
		instance.setClienteUid(clienteUid);
		return this;
	}

	public BaixaCreateRequestBuilder withTipoBaixaUid(UUID tipoBaixaUid) {
		instance.setTipoBaixaUid(tipoBaixaUid);
		return this;
	}

	public BaixaCreateRequestBuilder withData(OffsetDateTime data) {
		instance.setData(data);
		return this;
	}

	public BaixaCreateRequestBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public BaixaCreateRequestBuilder withObservacao(String observacao) {
		instance.setObservacao(observacao);
		return this;
	}

	public BaixaCreateRequestBuilder withDefaultValues() {
		return this.withData(OffsetDateTime.now()).withValor(BigDecimal.TEN).withObservacao("observacao")
				.withClienteUid(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"))
				.withTipoBaixaUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"));
	}

	public BaixaCreateRequest build() {
		return instance;
	}
}
