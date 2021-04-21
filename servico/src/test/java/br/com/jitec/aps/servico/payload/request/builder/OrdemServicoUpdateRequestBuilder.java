package br.com.jitec.aps.servico.payload.request.builder;

import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.payload.request.OrdemServicoUpdateRequest;

public class OrdemServicoUpdateRequestBuilder {

	private OrdemServicoUpdateRequest instance;

	private OrdemServicoUpdateRequestBuilder() {
		instance = new OrdemServicoUpdateRequest();
	}

	public static OrdemServicoUpdateRequestBuilder create() {
		return new OrdemServicoUpdateRequestBuilder();
	}

	public OrdemServicoUpdateRequestBuilder withTipoServicoUid(UUID tipoServicoUid) {
		instance.setTipoServicoUid(tipoServicoUid);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withContato(String contato) {
		instance.setContato(contato);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withDescricao(String descricao) {
		instance.setDescricao(descricao);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withObservacao(String observacao) {
		instance.setObservacao(observacao);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withEntrada(OffsetDateTime entrada) {
		instance.setEntrada(entrada);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withAgendadoPara(OffsetDateTime agendadoPara) {
		instance.setAgendadoPara(agendadoPara);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withEntrega(OffsetDateTime entrega) {
		instance.setEntrega(entrega);
		return this;
	}

	public OrdemServicoUpdateRequestBuilder withDefaultValues() {
		return this.withTipoServicoUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b")).withContato("contato")
				.withDescricao("descricao").withObservacao("observacao").withEntrada(OffsetDateTime.now())
				.withAgendadoPara(OffsetDateTime.now()).withEntrega(OffsetDateTime.now());
	}

	public OrdemServicoUpdateRequest build() {
		return instance;
	}
}
