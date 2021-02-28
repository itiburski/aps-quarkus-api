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

import br.com.jitec.aps.servico.api.ApiConstants;
import br.com.jitec.aps.servico.business.service.TipoServicoService;
import br.com.jitec.aps.servico.rest.payload.mapper.TipoServicoMapper;
import br.com.jitec.aps.servico.rest.payload.response.TipoServicoResponse;

@Tag(name = ApiConstants.TAG_TIPO_SERVICO)
@Path("/tipos-servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoServicoResource {

	@Inject
	TipoServicoMapper mapper;

	@Inject
	TipoServicoService service;

	@Operation(summary = ApiConstants.TIPO_SERVICO_LIST_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ApiConstants.TIPO_SERVICO_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public List<TipoServicoResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

}
