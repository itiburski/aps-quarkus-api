package br.com.jitec.aps.servico.business.data;

import java.time.LocalDate;
import java.util.UUID;

public class FaturaFilter {

	private UUID clienteUid;
	private Integer codigo;
	private LocalDate dataFrom;
	private LocalDate dataTo;

	public FaturaFilter() {
		// Default constructor
	}

	public FaturaFilter(UUID clienteUid, Integer codigo, LocalDate dataFrom, LocalDate dataTo) {
		super();
		this.clienteUid = clienteUid;
		this.codigo = codigo;
		this.dataFrom = dataFrom;
		this.dataTo = dataTo;
	}

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
