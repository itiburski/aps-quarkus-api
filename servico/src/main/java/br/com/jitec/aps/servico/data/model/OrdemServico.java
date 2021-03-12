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

@Entity
@Table(name = "ORDEM_SERVICO")
public class OrdemServico extends APSEntity {

	@Column(name = "NUMERO")
	private BigInteger numero;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_REPLICA_ID")
	private ClienteReplica cliente;

	@ManyToOne
	@JoinColumn(name = "TIPO_SERVICO_ID")
	private TipoServico tipoServico;

	@Column(name = "VALOR")
	private BigDecimal valor;

	@Column(name = "CONTATO")
	private String contato;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "OBSERVACAO")
	private String observacao;

	@Column(name = "DT_ENTRADA")
	private OffsetDateTime entrada;

	@Column(name = "DT_AGENDA")
	private OffsetDateTime agendadoPara;

	@Column(name = "DT_CONCLUSAO")
	private OffsetDateTime conclusao;

	@Column(name = "DT_ENTREGA")
	private OffsetDateTime entrega;

	// TODO
	// FATURA_ID BIGINT

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public ClienteReplica getCliente() {
		return cliente;
	}

	public void setCliente(ClienteReplica cliente) {
		this.cliente = cliente;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public OffsetDateTime getEntrada() {
		return entrada;
	}

	public void setEntrada(OffsetDateTime entrada) {
		this.entrada = entrada;
	}

	public OffsetDateTime getAgendadoPara() {
		return agendadoPara;
	}

	public void setAgendadoPara(OffsetDateTime agendadoPara) {
		this.agendadoPara = agendadoPara;
	}

	public OffsetDateTime getConclusao() {
		return conclusao;
	}

	public void setConclusao(OffsetDateTime conclusao) {
		this.conclusao = conclusao;
	}

	public OffsetDateTime getEntrega() {
		return entrega;
	}

	public void setEntrega(OffsetDateTime entrega) {
		this.entrega = entrega;
	}

}
