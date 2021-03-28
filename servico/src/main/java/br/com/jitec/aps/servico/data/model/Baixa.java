package br.com.jitec.aps.servico.data.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Table(name = "BAIXA")
@Entity
public class Baixa extends APSEntity {

	@ManyToOne
	@JoinColumn(name = "CLIENTE_REPLICA_ID")
	private ClienteReplica cliente;

	@ManyToOne
	@JoinColumn(name = "TIPO_BAIXA_ID")
	private TipoBaixa tipoBaixa;

	@Column(name = "DATA")
	private OffsetDateTime data;

	@Column(name = "VALOR")
	private BigDecimal valor;

	@Column(name = "OBSERVACAO")
	private String observacao;

	public ClienteReplica getCliente() {
		return cliente;
	}

	public void setCliente(ClienteReplica cliente) {
		this.cliente = cliente;
	}

	public TipoBaixa getTipoBaixa() {
		return tipoBaixa;
	}

	public void setTipoBaixa(TipoBaixa tipoBaixa) {
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
