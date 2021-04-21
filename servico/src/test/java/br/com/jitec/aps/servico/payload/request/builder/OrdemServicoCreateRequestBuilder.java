package br.com.jitec.aps.servico.payload.request.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.payload.request.OrdemServicoCreateRequest;

public class OrdemServicoCreateRequestBuilder {

	private OrdemServicoCreateRequest instance;

	private OrdemServicoCreateRequestBuilder() {
		instance = new OrdemServicoCreateRequest();
	}

	public static OrdemServicoCreateRequestBuilder create() {
		return new OrdemServicoCreateRequestBuilder();
	}

	public OrdemServicoCreateRequestBuilder withClienteUid(UUID clienteUid) {
		instance.setClienteUid(clienteUid);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withTipoServicoUid(UUID tipoServicoUid) {
		instance.setTipoServicoUid(tipoServicoUid);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withContato(String contato) {
		instance.setContato(contato);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withDescricao(String descricao) {
		instance.setDescricao(descricao);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withObservacao(String observacao) {
		instance.setObservacao(observacao);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withEntrada(OffsetDateTime entrada) {
		instance.setEntrada(entrada);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withAgendadoPara(OffsetDateTime agendadoPara) {
		instance.setAgendadoPara(agendadoPara);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withEntrega(OffsetDateTime entrega) {
		instance.setEntrega(entrega);
		return this;
	}

	public OrdemServicoCreateRequestBuilder withDefaultValues() {
		return this.withClienteUid(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"))
				.withTipoServicoUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"))
				.withValor(BigDecimal.ONE).withContato("contato").withDescricao("descricao")
				.withObservacao("observacao").withEntrada(OffsetDateTime.now()).withAgendadoPara(OffsetDateTime.now())
				.withEntrega(OffsetDateTime.now());
	}
	
	public OrdemServicoCreateRequest build() {
		return instance;
	}

}
