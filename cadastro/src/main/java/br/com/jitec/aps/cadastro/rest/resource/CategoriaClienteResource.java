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

import br.com.jitec.aps.cadastro.api.CadastroApiConstants;
import br.com.jitec.aps.cadastro.business.service.CategoriaClienteService;
import br.com.jitec.aps.cadastro.payload.mapper.CategoriaClienteMapper;
import br.com.jitec.aps.cadastro.payload.request.CategoriaClienteRequest;
import br.com.jitec.aps.cadastro.payload.response.CategoriaClienteResponse;
import br.com.jitec.aps.cadastro.payload.response.CategoriaClienteSlimResponse;

@Tag(name = CadastroApiConstants.TAG_CATEGORIAS_CLIENTE)
@Path("/categorias-cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaClienteResource {

	@Inject
	CategoriaClienteService service;

	@Inject
	CategoriaClienteMapper mapper;

	@Operation(summary = CadastroApiConstants.CATEGORIA_CLIENTE_LIST_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CATEGORIA_CLIENTE_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public List<CategoriaClienteSlimResponse> getAll() {
		return mapper.toListSlimResponse(service.getAll());
	}

	@Operation(summary = CadastroApiConstants.CATEGORIA_CLIENTE_GET_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CATEGORIA_CLIENTE_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("{categoriaClienteUid}")
	public CategoriaClienteResponse get(@PathParam UUID categoriaClienteUid) {
		return mapper.toResponse(service.get(categoriaClienteUid));
	}

	@Operation(summary = CadastroApiConstants.CATEGORIA_CLIENTE_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = CadastroApiConstants.CATEGORIA_CLIENTE_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = CategoriaClienteResponse.class))),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull CategoriaClienteRequest request) {
		CategoriaClienteResponse categoriaClienteResponse = mapper.toResponse(service.create(request.getDescricao()));
		return Response.status(Status.CREATED).entity(categoriaClienteResponse).build();
	}

	@Operation(summary = CadastroApiConstants.CATEGORIA_CLIENTE_UPDATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CadastroApiConstants.CATEGORIA_CLIENTE_UPDATE_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = CadastroApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("{categoriaClienteUid}/version/{version}")
	public CategoriaClienteResponse update(@PathParam UUID categoriaClienteUid, @PathParam Integer version,
			@Valid @NotNull CategoriaClienteRequest request) {
		return mapper.toResponse(service.update(categoriaClienteUid, version, request.getDescricao()));
	}

	@Operation(summary = CadastroApiConstants.CATEGORIA_CLIENTE_DELETE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = CadastroApiConstants.CATEGORIA_CLIENTE_DELETE_RESPONSE),
			@APIResponse(responseCode = "400", description = CadastroApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = CadastroApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = CadastroApiConstants.STATUS_CODE_SERVER_ERROR) })
	@DELETE
	@Path("{categoriaClienteUid}/version/{version}")
	public void delete(@PathParam UUID categoriaClienteUid, @PathParam Integer version) {
		service.delete(categoriaClienteUid, version);
	}

}
