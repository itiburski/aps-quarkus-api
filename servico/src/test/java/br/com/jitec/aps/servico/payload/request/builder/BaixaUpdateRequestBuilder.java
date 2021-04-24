package br.com.jitec.aps.servico.payload.request.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.payload.request.BaixaUpdateRequest;

public class BaixaUpdateRequestBuilder {

	private BaixaUpdateRequest instance;

	private BaixaUpdateRequestBuilder() {
		instance = new BaixaUpdateRequest();
	}

	public static BaixaUpdateRequestBuilder create() {
		return new BaixaUpdateRequestBuilder();
	}

	public BaixaUpdateRequestBuilder withTipoBaixaUid(UUID tipoBaixaUid) {
		instance.setTipoBaixaUid(tipoBaixaUid);
		return this;
	}

	public BaixaUpdateRequestBuilder withData(OffsetDateTime data) {
		instance.setData(data);
		return this;
	}

	public BaixaUpdateRequestBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public BaixaUpdateRequestBuilder withObservacao(String observacao) {
		instance.setObservacao(observacao);
		return this;
	}

	public BaixaUpdateRequestBuilder withDefaultValues() {
		return this.withData(OffsetDateTime.now()).withValor(BigDecimal.ONE).withObservacao("observacao-alterada")
				.withTipoBaixaUid(UUID.fromString("f156b5f4-7b7d-4697-ad8d-1ce5318005f2"));
	}

	public BaixaUpdateRequest build() {
		return instance;
	}

}
