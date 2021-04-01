package br.com.jitec.aps.servico.rest.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.jitec.aps.servico.api.ServicoApiConstants;
import br.com.jitec.aps.servico.business.service.TipoBaixaService;
import br.com.jitec.aps.servico.rest.payload.mapper.TipoBaixaMapper;
import br.com.jitec.aps.servico.rest.payload.response.TipoBaixaResponse;

@Tag(name = ServicoApiConstants.TAG_TIPOS_BAIXA)
@Path("/tipos-baixa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoBaixaResource {

	@Inject
	TipoBaixaMapper mapper;

	@Inject
	TipoBaixaService service;

	@Operation(summary = ServicoApiConstants.TIPO_BAIXA_LIST_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.TIPO_BAIXA_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public List<TipoBaixaResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

}
