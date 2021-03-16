package br.com.jitec.aps.servico.rest.payload.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrdemServicoConclusaoRequest {

	@NotNull
	private OffsetDateTime conclusao;

	@NotNull
	@Positive
	private BigDecimal valor;

	public OffsetDateTime getConclusao() {
		return conclusao;
	}

	public void setConclusao(OffsetDateTime conclusao) {
		this.conclusao = conclusao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
