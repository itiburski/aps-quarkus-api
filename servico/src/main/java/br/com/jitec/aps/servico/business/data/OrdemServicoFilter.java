package br.com.jitec.aps.servico.business.data;

import java.time.LocalDate;
import java.util.UUID;

public class OrdemServicoFilter {

	private UUID clienteUid;
	private LocalDate entradaFrom;
	private LocalDate entradaTo;
	private Boolean entregue;

	public OrdemServicoFilter() {
		// Default constructor
	}

	public OrdemServicoFilter(UUID clienteUid, LocalDate entradaFrom, LocalDate entradaTo, Boolean entregue) {
		super();
		this.clienteUid = clienteUid;
		this.entradaFrom = entradaFrom;
		this.entradaTo = entradaTo;
		this.entregue = entregue;
	}

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public LocalDate getEntradaFrom() {
		return entradaFrom;
	}

	public void setEntradaFrom(LocalDate entradaFrom) {
		this.entradaFrom = entradaFrom;
	}

	public LocalDate getEntradaTo() {
		return entradaTo;
	}

	public void setEntradaTo(LocalDate entradaTo) {
		this.entradaTo = entradaTo;
	}

	public Boolean getEntregue() {
		return entregue;
	}

	public void setEntregue(Boolean entregue) {
		this.entregue = entregue;
	}

}
