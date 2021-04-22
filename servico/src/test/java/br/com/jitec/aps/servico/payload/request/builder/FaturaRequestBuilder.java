package br.com.jitec.aps.servico.payload.request.builder;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.com.jitec.aps.servico.payload.request.FaturaRequest;

public class FaturaRequestBuilder {

	private FaturaRequest instance;

	private FaturaRequestBuilder() {
		instance = new FaturaRequest();
	}

	public static FaturaRequestBuilder create() {
		return new FaturaRequestBuilder();
	}

	public FaturaRequestBuilder withData(OffsetDateTime data) {
		instance.setData(data);
		return this;
	}

	public FaturaRequestBuilder withOrdensServicoUid(List<UUID> ordensServicoUid) {
		instance.setOrdensServicoUid(ordensServicoUid);
		return this;
	}

	public FaturaRequestBuilder withDefaultValues() {
		List<UUID> uids = Arrays.asList(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"),
				UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"));
		return this.withData(OffsetDateTime.now()).withOrdensServicoUid(uids);
	}

	public FaturaRequest build() {
		return instance;
	}

}
