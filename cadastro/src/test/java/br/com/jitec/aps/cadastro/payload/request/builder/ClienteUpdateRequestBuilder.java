package br.com.jitec.aps.cadastro.payload.request.builder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.com.jitec.aps.cadastro.payload.request.ClienteEmailUpdateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneUpdateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteUpdateRequest;

public class ClienteUpdateRequestBuilder {

	private ClienteUpdateRequest instance;

	private ClienteUpdateRequestBuilder() {
		instance = new ClienteUpdateRequest();
	}

	public static ClienteUpdateRequestBuilder create() {
		return new ClienteUpdateRequestBuilder();
	}

	public ClienteUpdateRequestBuilder withAtivo(Boolean ativo) {
		instance.setAtivo(ativo);
		return this;
	}

	public ClienteUpdateRequestBuilder withNome(String nome) {
		instance.setNome(nome);
		return this;
	}

	public ClienteUpdateRequestBuilder withRazaoSocial(String razaoSocial) {
		instance.setRazaoSocial(razaoSocial);
		return this;
	}

	public ClienteUpdateRequestBuilder withContato(String contato) {
		instance.setContato(contato);
		return this;
	}

	public ClienteUpdateRequestBuilder withRua(String rua) {
		instance.setRua(rua);
		return this;
	}

	public ClienteUpdateRequestBuilder withComplemento(String complemento) {
		instance.setComplemento(complemento);
		return this;
	}

	public ClienteUpdateRequestBuilder withBairro(String bairro) {
		instance.setBairro(bairro);
		return this;
	}

	public ClienteUpdateRequestBuilder withCep(String cep) {
		instance.setCep(cep);
		return this;
	}

	public ClienteUpdateRequestBuilder withHomepage(String homepage) {
		instance.setHomepage(homepage);
		return this;
	}

	public ClienteUpdateRequestBuilder withCnpj(String cnpj) {
		instance.setCnpj(cnpj);
		return this;
	}

	public ClienteUpdateRequestBuilder withInscricaoEstadual(String inscricaoEstadual) {
		instance.setInscricaoEstadual(inscricaoEstadual);
		return this;
	}

	public ClienteUpdateRequestBuilder withCidadeUid(UUID cidadeUid) {
		instance.setCidadeUid(cidadeUid);
		return this;
	}

	public ClienteUpdateRequestBuilder withCategoriaClienteUid(UUID categoriaClienteUid) {
		instance.setCategoriaClienteUid(categoriaClienteUid);
		return this;
	}

	public ClienteUpdateRequestBuilder withEmails(List<ClienteEmailUpdateRequest> emails) {
		instance.setEmails(emails);
		return this;
	}

	public ClienteUpdateRequestBuilder withTelefones(List<ClienteTelefoneUpdateRequest> telefones) {
		instance.setTelefones(telefones);
		return this;
	}

	public ClienteUpdateRequestBuilder withDefaultValues() {
		List<ClienteEmailUpdateRequest> emails = Arrays
				.asList(ClienteEmailUpdateRequestBuilder.create().withEmail("email@email.com").build());

		List<ClienteTelefoneUpdateRequest> telefones = Arrays
				.asList(ClienteTelefoneUpdateRequestBuilder.create().withNumero(111222333)
						.withTipoTelefoneUid(UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")).build());

		return this.withNome("nome").withRazaoSocial("razaoSocial").withContato("contato").withRua("rua")
				.withComplemento("complemento").withBairro("bairro").withCep("cep").withHomepage("homepage")
				.withCnpj("cnpj").withInscricaoEstadual("inscricaoEstadual")
				.withCidadeUid(UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"))
				.withCategoriaClienteUid(UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")).withEmails(emails)
				.withTelefones(telefones);
	}

	public ClienteUpdateRequest build() {
		return instance;
	}

}
