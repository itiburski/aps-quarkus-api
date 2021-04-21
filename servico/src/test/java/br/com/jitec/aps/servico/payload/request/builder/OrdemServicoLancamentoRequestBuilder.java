package br.com.jitec.aps.servico.payload.request.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import br.com.jitec.aps.servico.payload.request.OrdemServicoLancamentoRequest;

public class OrdemServicoLancamentoRequestBuilder {

	private OrdemServicoLancamentoRequest instance;

	private OrdemServicoLancamentoRequestBuilder() {
		instance = new OrdemServicoLancamentoRequest();
	}

	public static OrdemServicoLancamentoRequestBuilder create() {
		return new OrdemServicoLancamentoRequestBuilder();
	}

	public OrdemServicoLancamentoRequestBuilder withLancamento(OffsetDateTime lancamento) {
		instance.setLancamento(lancamento);
		return this;
	}

	public OrdemServicoLancamentoRequestBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public OrdemServicoLancamentoRequestBuilder withDefaultValues() {
		return this.withLancamento(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")))
				.withValor(new BigDecimal("123"));
	}

	public OrdemServicoLancamentoRequest build() {
		return instance;
	}

}
