package br.com.jitec.aps.commons.business.data;

import java.math.BigDecimal;
import java.util.UUID;

public class ClienteSaldoDto {

	private UUID clienteUid;
	private BigDecimal variacaoSaldo;

	public ClienteSaldoDto() {
		// Default constructor
	}

	public ClienteSaldoDto(UUID clienteUid, BigDecimal variacaoSaldo) {
		super();
		this.clienteUid = clienteUid;
		this.variacaoSaldo = variacaoSaldo;
	}

	public UUID getClienteUid() {
		return clienteUid;
	}

	public void setClienteUid(UUID clienteUid) {
		this.clienteUid = clienteUid;
	}

	public BigDecimal getVariacaoSaldo() {
		return variacaoSaldo;
	}

	public void setVariacaoSaldo(BigDecimal variacaoSaldo) {
		this.variacaoSaldo = variacaoSaldo;
	}

}
