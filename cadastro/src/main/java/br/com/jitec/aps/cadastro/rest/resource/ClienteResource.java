package br.com.jitec.aps.cadastro.rest.resource;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

import br.com.jitec.aps.cadastro.api.CadastroApiConstants;
import br.com.jitec.aps.cadastro.business.data.ClienteFilter;
import br.com.jitec.aps.cadastro.business.service.ClienteService;
import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.payload.mapper.ClienteMapper;
import br.com.jitec.aps.cadastro.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteUpdateRequest;
import br.com.jitec.aps.cadastro.payload.response.ClienteResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteSlimResponse;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.rest.http.Headers;

@Tag(name = CadastroApiConstants.TAG_CLIENTES)
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

	@Inject
	ClienteService service;

	@Inject
	ClienteMapper mapper;

	@Operation(summary = CadastroApiConstants.CLIENTE_LIST_OPERATION, description = CadastroApiConstants.CLIENTE_LIST_OPERATION_DESCRIPTION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CLIENTE_LIST_RESPONSE, content = @Content(schema = @Schema(allOf = ClienteSlimResponse.class))),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public Response getClientes(@QueryParam("page") Integer page, @QueryParam("size") Integer size,
			@QueryParam("codigo") Integer codigo, @QueryParam("nomeOuRazaoSocial") String nomeOuRazaoSocial,
			@QueryParam("ativo") Boolean ativo, @QueryParam("sort") String sort) {

		Pagination pagination = Pagination.builder().withPage(page).withSize(size).build();
		ClienteFilter filter = new ClienteFilter(codigo, nomeOuRazaoSocial, ativo);

		Paged<Cliente> query = service.getClientes(pagination, filter, sort);
		List<ClienteSlimResponse> clientes = mapper.toListSlimResponse(query.getContent());

		return Response.ok(clientes).header(Headers.PAGE_NUMBER, pagination.getPage())
				.header(Headers.PAGE_SIZE, pagination.getSize()).header(Headers.TOTAL_PAGES, query.getPageCount())
				.header(Headers.TOTAL_ITEMS, query.getItemCount()).build();
	}

	@Operation(summary = CadastroApiConstants.CLIENTE_GET_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = CadastroApiConstants.CLIENTE_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("{clienteUid}")
	public ClienteResponse get(@PathParam UUID clienteUid) {
		return mapper.toResponse(service.getComplete(clienteUid));
	}

	@Operation(summary = CadastroApiConstants.CLIENTE_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = CadastroApiConstants.CLIENTE_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = ClienteResponse.class))),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull ClienteCreateRequest request) {
		ClienteResponse clienteResponse = mapper.toResponse(service.create(request));
		return Response.status(Status.CREATED).entity(clienteResponse).build();
	}

	@Operation(summary = CadastroApiConstants.CLIENTE_UPDATEALL_OPERATION, description = CadastroApiConstants.CLIENTE_UPDATEALL_OPERATION_DESCRIPTION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CLIENTE_UPDATEALL_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("{clienteUid}/version/{version}")
	public ClienteResponse updateAll(@PathParam UUID clienteUid, @PathParam Integer version,
			@Valid @NotNull ClienteUpdateRequest request) {

		return mapper.toResponse(service.updateAll(clienteUid, version, request));
	}

	@Operation(summary = CadastroApiConstants.CLIENTE_UPDATENOTNULL_OPERATION, description = CadastroApiConstants.CLIENTE_UPDATENOTNULL_OPERATION_DESCRIPTION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CLIENTE_UPDATENOTNULL_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PATCH
	@Path("{clienteUid}/version/{version}")
	public ClienteResponse updateNotNull(@PathParam UUID clienteUid, @PathParam Integer version,
			@Valid @NotNull ClienteUpdateRequest request) {

		return mapper.toResponse(service.updateNotNull(clienteUid, version, request));
	}

	@Operation(summary = CadastroApiConstants.CLIENTE_DELETE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = CadastroApiConstants.CLIENTE_DELETE_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@DELETE
	@Path("{clienteUid}/version/{version}")
	public void delete(@PathParam UUID clienteUid, @PathParam Integer version) {
		service.delete(clienteUid, version);
	}

}
