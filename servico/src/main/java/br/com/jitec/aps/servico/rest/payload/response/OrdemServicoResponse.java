package br.com.jitec.aps.servico.rest.payload.response;

public class OrdemServicoResponse extends OrdemServicoSimpleResponse {

	private String observacao;

	private Integer version;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
