package br.com.jitec.aps.servico.payload.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class GenericBaixaRequest {

	@NotNull
	private UUID tipoBaixaUid;

	@NotNull
	private OffsetDateTime data;

	@NotNull
	@Positive
	private BigDecimal valor;

	@Size(max = 8192)
	private String observacao;

	public UUID getTipoBaixaUid() {
		return tipoBaixaUid;
	}

	public void setTipoBaixaUid(UUID tipoBaixaUid) {
		this.tipoBaixaUid = tipoBaixaUid;
	}

	public OffsetDateTime getData() {
		return data;
	}

	public void setData(OffsetDateTime data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
