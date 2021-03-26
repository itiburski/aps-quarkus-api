package br.com.jitec.aps.servico.data.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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
	private Integer numero;

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

	@Column(name = "DT_LANCAMENTO")
	private OffsetDateTime lancamento;

	@Column(name = "DT_ENTREGA")
	private OffsetDateTime entrega;

	@ManyToOne
	@JoinColumn(name = "FATURA_ID")
	private Fatura fatura;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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

	public OffsetDateTime getLancamento() {
		return lancamento;
	}

	public void setLancamento(OffsetDateTime lancamento) {
		this.lancamento = lancamento;
	}

	public OffsetDateTime getEntrega() {
		return entrega;
	}

	public void setEntrega(OffsetDateTime entrega) {
		this.entrega = entrega;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public static OrdemServico.Builder builder() {
		return new OrdemServico.Builder();
	}

	public static class Builder {
		private OrdemServico instance;

		public Builder() {
			instance = new OrdemServico();
		}

		public Builder withUid(UUID uid) {
			instance.setUid(uid);
			return this;
		}

		public Builder withCliente(ClienteReplica cliente) {
			instance.setCliente(cliente);
			return this;
		}

		public Builder withLancamento(OffsetDateTime lancamento) {
			instance.setLancamento(lancamento);
			return this;
		}

		public Builder withValor(BigDecimal valor) {
			instance.setValor(valor);
			return this;
		}

		public Builder withFatura(Fatura fatura) {
			instance.setFatura(fatura);
			return this;
		}

		public OrdemServico build() {
			return instance;
		}
	}

}
