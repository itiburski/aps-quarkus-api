package br.com.jitec.aps.servico.data.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Table(name = "FATURA")
@Entity
public class Fatura extends APSEntity {

	@Column(name = "CODIGO")
	private BigInteger codigo;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_REPLICA_ID")
	private ClienteReplica cliente;

	@Column(name = "DATA")
	private OffsetDateTime data;

	@Column(name = "VALOR_TOTAL")
	private BigDecimal valorTotal;

	public BigInteger getCodigo() {
		return codigo;
	}

	public void setCodigo(BigInteger codigo) {
		this.codigo = codigo;
	}

	public ClienteReplica getCliente() {
		return cliente;
	}

	public void setCliente(ClienteReplica cliente) {
		this.cliente = cliente;
	}

	public OffsetDateTime getData() {
		return data;
	}

	public void setData(OffsetDateTime data) {
		this.data = data;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
