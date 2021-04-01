package br.com.jitec.aps.servico.business.data;

import java.time.LocalDate;
import java.util.UUID;

public class BaixaFilter {

	private UUID clienteUid;
	private UUID tipoBaixaUid;
	private LocalDate dataFrom;
	private LocalDate dataTo;

	public BaixaFilter() {
		// Default constructor
	}

	public BaixaFilter(UUID clienteUid, UUID tipoBaixaUid, LocalDate dataFrom, LocalDate dataTo) {
		super();
		this.clienteUid = clienteUid;
		this.tipoBaixaUid = tipoBaixaUid;
		this.dataFrom = dataFrom;
		this.dataTo = dataTo;
	}

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public UUID getTipoBaixaUid() {
		return tipoBaixaUid;
	}

	public void setTipoBaixaUid(UUID tipoBaixaUid) {
		this.tipoBaixaUid = tipoBaixaUid;
	}

	public LocalDate getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(LocalDate dataFrom) {
		this.dataFrom = dataFrom;
	}

	public LocalDate getDataTo() {
		return dataTo;
	}

	public void setDataTo(LocalDate dataTo) {
		this.dataTo = dataTo;
	}

}
