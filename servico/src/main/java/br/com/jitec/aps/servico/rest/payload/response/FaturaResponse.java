package br.com.jitec.aps.servico.rest.payload.response;

import java.util.List;

public class FaturaResponse extends FaturaSimpleResponse {

	private List<OrdemServicoSimpleResponse> ordensServico;

	public List<OrdemServicoSimpleResponse> getOrdensServico() {
		return ordensServico;
	}

	public void setOrdensServico(List<OrdemServicoSimpleResponse> ordensServico) {
		this.ordensServico = ordensServico;
	}

}
