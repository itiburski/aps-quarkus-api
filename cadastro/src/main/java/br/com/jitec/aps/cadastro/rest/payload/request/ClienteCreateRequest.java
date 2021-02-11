package br.com.jitec.aps.cadastro.rest.payload.request;

public class ClienteCreateRequest extends GenericClienteRequest {

	private Integer codigo;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
}
