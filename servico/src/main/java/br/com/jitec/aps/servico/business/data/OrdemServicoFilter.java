package br.com.jitec.aps.servico.business.data;

import java.time.LocalDate;
import java.util.UUID;

public class OrdemServicoFilter {

	private UUID clienteUid;
	private LocalDate entradaFrom;
	private LocalDate entradaTo;
	private Boolean entregue;
	private Boolean lancado;
	private Boolean faturado;

	public OrdemServicoFilter() {
		// Default constructor
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

	public Boolean getLancado() {
		return lancado;
	}

	public void setLancado(Boolean lancado) {
		this.lancado = lancado;
	}

	public Boolean getFaturado() {
		return faturado;
	}

	public void setFaturado(Boolean faturado) {
		this.faturado = faturado;
	}

	public static OrdemServicoFilter.Builder builder() {
		return new OrdemServicoFilter.Builder();
	}

	public static class Builder {
		private OrdemServicoFilter instance;

		public Builder() {
			instance = new OrdemServicoFilter();
		}

		public Builder withClienteUid(UUID clienteUid) {
			instance.setClienteUid(clienteUid);
			return this;
		}

		public Builder withEntradaFrom(LocalDate entradaFrom) {
			instance.setEntradaFrom(entradaFrom);
			return this;
		}

		public Builder withEntradaTo(LocalDate entradaTo) {
			instance.setEntradaTo(entradaTo);
			return this;
		}

		public Builder withEntregue(Boolean entregue) {
			instance.setEntregue(entregue);
			return this;
		}

		public Builder withLancado(Boolean lancado) {
			instance.setLancado(lancado);
			return this;
		}

		public Builder withFaturado(Boolean faturado) {
			instance.setFaturado(faturado);
			return this;
		}

		public OrdemServicoFilter build() {
			return instance;
		}
	}

}
