package br.com.jitec.aps.cadastro.payload.request;

import java.util.List;

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

}
