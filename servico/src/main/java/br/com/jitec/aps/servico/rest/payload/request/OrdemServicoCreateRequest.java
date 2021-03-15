package br.com.jitec.aps.servico.rest.payload.request;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public class OrdemServicoCreateRequest extends GenericOrdemServicoRequest {

	@NotNull
	private UUID clienteUid;

	private BigDecimal valor;

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
