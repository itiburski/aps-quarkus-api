package br.com.jitec.aps.cadastro.payload.request.builder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.com.jitec.aps.cadastro.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneCreateRequest;

public class ClienteCreateRequestBuilder {

	private ClienteCreateRequest instance;

	private ClienteCreateRequestBuilder() {
		instance = new ClienteCreateRequest();
	}

	public static ClienteCreateRequestBuilder create() {
		return new ClienteCreateRequestBuilder();
	}

	public ClienteCreateRequestBuilder withNome(String nome) {
		instance.setNome(nome);
		return this;
	}

	public ClienteCreateRequestBuilder withRazaoSocial(String razaoSocial) {
		instance.setRazaoSocial(razaoSocial);
		return this;
	}

	public ClienteCreateRequestBuilder withContato(String contato) {
		instance.setContato(contato);
		return this;
	}

	public ClienteCreateRequestBuilder withRua(String rua) {
		instance.setRua(rua);
		return this;
	}

	public ClienteCreateRequestBuilder withComplemento(String complemento) {
		instance.setComplemento(complemento);
		return this;
	}

	public ClienteCreateRequestBuilder withBairro(String bairro) {
		instance.setBairro(bairro);
		return this;
	}

	public ClienteCreateRequestBuilder withCep(String cep) {
		instance.setCep(cep);
		return this;
	}

	public ClienteCreateRequestBuilder withHomepage(String homepage) {
		instance.setHomepage(homepage);
		return this;
	}

	public ClienteCreateRequestBuilder withCnpj(String cnpj) {
		instance.setCnpj(cnpj);
		return this;
	}

	public ClienteCreateRequestBuilder withInscricaoEstadual(String inscricaoEstadual) {
		instance.setInscricaoEstadual(inscricaoEstadual);
		return this;
	}

	public ClienteCreateRequestBuilder withCidadeUid(UUID cidadeUid) {
		instance.setCidadeUid(cidadeUid);
		return this;
	}

	public ClienteCreateRequestBuilder withCategoriaClienteUid(UUID categoriaClienteUid) {
		instance.setCategoriaClienteUid(categoriaClienteUid);
		return this;
	}

	public ClienteCreateRequestBuilder withEmails(List<ClienteEmailCreateRequest> emails) {
		instance.setEmails(emails);
		return this;
	}

	public ClienteCreateRequestBuilder withTelefones(List<ClienteTelefoneCreateRequest> telefones) {
		instance.setTelefones(telefones);
		return this;
	}

	public ClienteCreateRequestBuilder withDefaultValues() {
		List<ClienteEmailCreateRequest> emails = Arrays
				.asList(ClienteEmailCreateRequestBuilder.create().withEmail("email@email.com").build());

		List<ClienteTelefoneCreateRequest> telefones = Arrays
				.asList(ClienteTelefoneCreateRequestBuilder.create().withNumero(111222333)
						.withTipoTelefoneUid(UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")).build());
		
		return this.withNome("nome").withRazaoSocial("razaoSocial").withContato("contato").withRua("rua")
				.withComplemento("complemento").withBairro("bairro").withCep("cep").withHomepage("homepage")
				.withCnpj("cnpj").withInscricaoEstadual("inscricaoEstadual")
				.withCidadeUid(UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"))
				.withCategoriaClienteUid(UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")).withEmails(emails)
				.withTelefones(telefones);
	}

	public ClienteCreateRequest build() {
		return instance;
	}

}
