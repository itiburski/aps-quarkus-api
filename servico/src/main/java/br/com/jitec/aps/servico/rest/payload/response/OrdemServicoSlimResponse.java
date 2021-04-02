package br.com.jitec.aps.servico.rest.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OrdemServicoSlimResponse {

	private UUID ordemServicoUid;
	private Integer numero;
	private ClienteSlimResponse cliente;
	private TipoServicoSlimResponse tipoServico;
	private BigDecimal valor;
	private String contato;
	private String descricao;
	private OffsetDateTime entrada;
	private OffsetDateTime agendadoPara;
	private OffsetDateTime lancamento;
	private OffsetDateTime entrega;
	private Integer codigoFatura;

	public UUID getOrdemServicoUid() {
		return ordemServicoUid;
	}

	public void setOrdemServicoUid(UUID ordemServicoUid) {
		this.ordemServicoUid = ordemServicoUid;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public ClienteSlimResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteSlimResponse cliente) {
		this.cliente = cliente;
	}

	public TipoServicoSlimResponse getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServicoSlimResponse tipoServico) {
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

	public Integer getCodigoFatura() {
		return codigoFatura;
	}

	public void setCodigoFatura(Integer codigoFatura) {
		this.codigoFatura = codigoFatura;
	}

}
