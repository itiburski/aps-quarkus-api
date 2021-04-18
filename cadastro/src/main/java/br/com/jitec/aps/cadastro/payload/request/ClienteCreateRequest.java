package br.com.jitec.aps.cadastro.payload.request;

import java.util.List;

import javax.validation.Valid;

public class ClienteCreateRequest extends GenericClienteRequest {

	@Valid
	private List<ClienteEmailCreateRequest> emails;

	@Valid
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

}
