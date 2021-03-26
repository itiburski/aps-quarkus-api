package br.com.jitec.aps.servico.data.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Table(name = "FATURA")
@Entity
public class Fatura extends APSEntity {

	@Column(name = "CODIGO")
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_REPLICA_ID")
	private ClienteReplica cliente;

	@Column(name = "DATA")
	private OffsetDateTime data;

	@Column(name = "VALOR_TOTAL")
	private BigDecimal valorTotal;

	@OneToMany(mappedBy = "fatura", fetch = FetchType.LAZY)
	private List<OrdemServico> ordensServico = new ArrayList<>();

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
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

	public List<OrdemServico> getOrdensServico() {
		return ordensServico;
	}

	public void setOrdensServico(List<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

}
