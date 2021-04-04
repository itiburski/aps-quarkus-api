package br.com.jitec.aps.servico.payload.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrdemServicoLancamentoRequest {

	@NotNull
	private OffsetDateTime lancamento;

	@NotNull
	@Positive
	private BigDecimal valor;

	public OffsetDateTime getLancamento() {
		return lancamento;
	}

	public void setLancamento(OffsetDateTime lancamento) {
		this.lancamento = lancamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
