package br.com.jitec.aps.cadastro.payload.request;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

public class ClienteUpdateRequest extends GenericClienteRequest {

	private Boolean ativo;

	@Valid
	private List<ClienteEmailUpdateRequest> emails;

	@Valid
	private List<ClienteTelefoneUpdateRequest> telefones;

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<ClienteEmailUpdateRequest> getEmails() {
		return emails;
	}

	public void setEmails(List<ClienteEmailUpdateRequest> emails) {
		this.emails = emails;
	}

	public List<ClienteTelefoneUpdateRequest> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<ClienteTelefoneUpdateRequest> telefones) {
		this.telefones = telefones;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClienteUpdateRequest instance;

		private Builder() {
			instance = new ClienteUpdateRequest();
		}

		public Builder withAtivo(Boolean ativo) {
			instance.setAtivo(ativo);
			return this;
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

		public Builder withEmails(List<ClienteEmailUpdateRequest> emails) {
			instance.setEmails(emails);
			return this;
		}

		public Builder withTelefones(List<ClienteTelefoneUpdateRequest> telefones) {
			instance.setTelefones(telefones);
			return this;
		}

		public ClienteUpdateRequest build() {
			return instance;
		}

	}
}
