package br.com.jitec.aps.servico.rest.payload.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OrdemServicoSimpleResponse {

	private UUID ordemServicoUid;

	private BigInteger numero;

	private UUID clienteUid;

	private TipoServicoResponse tipoServico;

	private BigDecimal valor;

	private String contato;

	private String descricao;

	private OffsetDateTime entrada;

	private OffsetDateTime agendadoPara;

	private OffsetDateTime conclusao;

	private OffsetDateTime entrega;

	public UUID getOrdemServicoUid() {
		return ordemServicoUid;
	}

	public void setOrdemServicoUid(UUID ordemServicoUid) {
		this.ordemServicoUid = ordemServicoUid;
	}

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public TipoServicoResponse getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServicoResponse tipoServico) {
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
