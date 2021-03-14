package br.com.jitec.aps.servico.rest.payload.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrdemServicoRequest {

	@NotNull
	private UUID clienteUid;

	@NotNull
	private UUID tipoServicoUid;

	private BigDecimal valor;

	@Size(max = 60)
	private String contato;

	@NotBlank
	@Size(max = 100)
	private String descricao;

	@Size(max = 8192)
	private String observacao;

	@NotNull
	private OffsetDateTime entrada;

	private OffsetDateTime agendadoPara;

	private OffsetDateTime conclusao;

	private OffsetDateTime entrega;

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public UUID getTipoServicoUid() {
		return tipoServicoUid;
	}

	public void setTipoServicoUid(UUID tipoServicoUid) {
		this.tipoServicoUid = tipoServicoUid;
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
