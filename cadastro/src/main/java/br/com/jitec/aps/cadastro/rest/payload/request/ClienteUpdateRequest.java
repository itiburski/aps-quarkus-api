package br.com.jitec.aps.cadastro.rest.payload.request;

public class ClienteUpdateRequest extends GenericClienteRequest {

	private Boolean ativo;

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
