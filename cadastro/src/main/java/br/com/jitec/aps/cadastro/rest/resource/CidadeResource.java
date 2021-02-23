package br.com.jitec.aps.cadastro.rest.resource;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.cadastro.api.ApiConstants;
import br.com.jitec.aps.cadastro.business.service.CidadeService;
import br.com.jitec.aps.cadastro.rest.payload.mapper.CidadeMapper;
import br.com.jitec.aps.cadastro.rest.payload.request.CidadeRequest;
import br.com.jitec.aps.cadastro.rest.payload.response.CidadeResponse;

@Tag(name = ApiConstants.TAG_CIDADES)
@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CidadeResource {

	@Inject
	CidadeService service;

	@Inject
	CidadeMapper mapper;

	@Operation(summary = ApiConstants.CIDADE_LIST_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ApiConstants.CIDADE_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public List<CidadeResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

	@Operation(summary = ApiConstants.CIDADE_GET_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ApiConstants.CIDADE_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("{cidadeUid}")
	public CidadeResponse get(@PathParam UUID cidadeUid) {
		return mapper.toResponse(service.get(cidadeUid));
	}

	@Operation(summary = ApiConstants.CIDADE_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ApiConstants.CIDADE_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = CidadeResponse.class))),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull CidadeRequest request) {
		CidadeResponse cidadeResponse = mapper.toResponse(service.create(request.getNome(), request.getUf()));
		return Response.status(Status.CREATED).entity(cidadeResponse).build();
	}

	@Operation(summary = ApiConstants.CIDADE_UPDATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ApiConstants.CIDADE_UPDATE_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("{cidadeUid}/version/{version}")
	public CidadeResponse update(@PathParam UUID cidadeUid, @PathParam Integer version,
			@Valid @NotNull CidadeRequest request) {
		return mapper.toResponse(service.update(cidadeUid, version, request.getNome(), request.getUf()));
	}

	@Operation(summary = ApiConstants.CIDADE_DELETE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = ApiConstants.CIDADE_DELETE_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@DELETE
	@Path("{cidadeUid}/version/{version}")
	public void delete(@PathParam UUID cidadeUid, @PathParam Integer version) {
		service.delete(cidadeUid, version);
	}

}
