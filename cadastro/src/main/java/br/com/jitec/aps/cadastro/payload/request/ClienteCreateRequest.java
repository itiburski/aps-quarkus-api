package br.com.jitec.aps.cadastro.payload.request;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

public class ClienteCreateRequest extends GenericClienteRequest {

	@Valid
	private List<ClienteEmailCreateRequest> emails;

	private List<ClienteTelefoneCreateRequest> telefones;

	public List<ClienteEmailCreateRequest> getEmails() {
		return emails;
	}

	public void setEmails(List<ClienteEmailCreateRequest> emails) {
		this.emails = emails;
	}

	public List<ClienteTelefoneCreateRequest> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<ClienteTelefoneCreateRequest> telefones) {
		this.telefones = telefones;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteCreateRequest instance;

		private Builder() {
			instance = new ClienteCreateRequest();
		}

		public Builder withNome(String nome) {
			instance.setNome(nome);
			return this;
		}

		public Builder withRazaoSocial(String razaoSocial) {
			instance.setRazaoSocial(razaoSocial);
			return this;
		}

		public Builder withContato(String contato) {
			instance.setContato(contato);
			return this;
		}

		public Builder withRua(String rua) {
			instance.setRua(rua);
			return this;
		}

		public Builder withComplemento(String complemento) {
			instance.setComplemento(complemento);
			return this;
		}

		public Builder withBairro(String bairro) {
			instance.setBairro(bairro);
			return this;
		}

		public Builder withCep(String cep) {
			instance.setCep(cep);
			return this;
		}

		public Builder withHomepage(String homepage) {
			instance.setHomepage(homepage);
			return this;
		}

		public Builder withCnpj(String cnpj) {
			instance.setCnpj(cnpj);
			return this;
		}

		public Builder withInscricaoEstadual(String inscricaoEstadual) {
			instance.setInscricaoEstadual(inscricaoEstadual);
			return this;
		}

		public Builder withCidadeUid(UUID cidadeUid) {
			instance.setCidadeUid(cidadeUid);
			return this;
		}

		public Builder withCategoriaClienteUid(UUID categoriaClienteUid) {
			instance.setCategoriaClienteUid(categoriaClienteUid);
			return this;
		}

		public Builder withEmails(List<ClienteEmailCreateRequest> emails) {
			instance.setEmails(emails);
			return this;
		}

		public Builder withTelefones(List<ClienteTelefoneCreateRequest> telefones) {
			instance.setTelefones(telefones);
			return this;
		}

		public ClienteCreateRequest build() {
			return instance;
		}

	}

}
