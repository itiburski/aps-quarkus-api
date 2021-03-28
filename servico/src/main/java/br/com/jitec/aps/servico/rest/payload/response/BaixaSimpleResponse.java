package br.com.jitec.aps.servico.rest.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaixaSimpleResponse {

	private UUID baixaUid;

	private ClienteResponse cliente;

	private TipoBaixaResponse tipoBaixa;

	private OffsetDateTime data;

	private BigDecimal valor;

	private Integer version;

	public UUID getBaixaUid() {
		return baixaUid;
	}

	public void setBaixaUid(UUID baixaUid) {
		this.baixaUid = baixaUid;
	}

	public ClienteResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResponse cliente) {
		this.cliente = cliente;
	}

	public TipoBaixaResponse getTipoBaixa() {
		return tipoBaixa;
	}

	public void setTipoBaixa(TipoBaixaResponse tipoBaixa) {
		this.tipoBaixa = tipoBaixa;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
