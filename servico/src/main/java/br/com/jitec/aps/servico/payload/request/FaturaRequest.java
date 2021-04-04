package br.com.jitec.aps.servico.payload.request;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FaturaRequest {

	@NotNull
	private OffsetDateTime data;

	@NotEmpty
	private List<UUID> ordensServicoUid;

	public OffsetDateTime getData() {
		return data;
	}

	public void setData(OffsetDateTime data) {
		this.data = data;
	}

	public List<UUID> getOrdensServicoUid() {
		return ordensServicoUid;
	}

	public void setOrdensServicoUid(List<UUID> ordensServicoUid) {
		this.ordensServicoUid = ordensServicoUid;
	}

}
