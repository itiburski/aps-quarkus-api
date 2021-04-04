package br.com.jitec.aps.servico.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaixaSlimResponse {

	private UUID baixaUid;
	private ClienteSlimResponse cliente;
	private TipoBaixaSlimResponse tipoBaixa;
	private OffsetDateTime data;
	private BigDecimal valor;

	public UUID getBaixaUid() {
		return baixaUid;
	}

	public void setBaixaUid(UUID baixaUid) {
		this.baixaUid = baixaUid;
	}

	public ClienteSlimResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteSlimResponse cliente) {
		this.cliente = cliente;
	}

	public TipoBaixaSlimResponse getTipoBaixa() {
		return tipoBaixa;
	}

	public void setTipoBaixa(TipoBaixaSlimResponse tipoBaixa) {
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

}
