package br.com.jitec.aps.servico.rest.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.jitec.aps.servico.api.ApiConstants;
import br.com.jitec.aps.servico.business.service.FaturaService;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.rest.payload.mapper.FaturaMapper;
import br.com.jitec.aps.servico.rest.payload.request.FaturaRequest;
import br.com.jitec.aps.servico.rest.payload.response.FaturaResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoResponse;

@Tag(name = ApiConstants.TAG_FATURA)
@Path("/faturas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaturaResource {

	@Inject
	FaturaService service;

	@Inject
	FaturaMapper faturaMapper;

	@Operation(summary = ApiConstants.FATURA_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ApiConstants.FATURA_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = FaturaResponse.class))),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull FaturaRequest request) {
		Fatura fatura = service.create(request.getData(), request.getOrdensServicoUid());
		FaturaResponse response = faturaMapper.toResponse(fatura);

		return Response.status(Status.CREATED).entity(response).build();
	}

}
