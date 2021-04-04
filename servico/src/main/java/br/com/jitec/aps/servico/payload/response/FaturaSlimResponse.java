package br.com.jitec.aps.servico.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class FaturaSlimResponse {

	private UUID faturaUid;
	private Integer codigo;
	private BigDecimal valorTotal;
	private OffsetDateTime data;
	private ClienteSlimResponse cliente;

	public UUID getFaturaUid() {
		return faturaUid;
	}

	public void setFaturaUid(UUID faturaUid) {
		this.faturaUid = faturaUid;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public OffsetDateTime getData() {
		return data;
	}

	public void setData(OffsetDateTime data) {
		this.data = data;
	}

	public ClienteSlimResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteSlimResponse cliente) {
		this.cliente = cliente;
	}

}
