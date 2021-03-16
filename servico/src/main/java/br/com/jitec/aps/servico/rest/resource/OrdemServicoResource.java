package br.com.jitec.aps.servico.rest.resource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import br.com.jitec.aps.servico.business.service.OrdemServicoService;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.rest.payload.mapper.OrdemServicoMapper;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoConclusaoRequest;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoCreateRequest;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoUpdateRequest;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoSimpleResponse;

@Tag(name = ApiConstants.TAG_ORDEM_SERVICO)
@Path("/ordens-servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdemServicoResource {

	@Inject
	OrdemServicoService osService;

	@Inject
	OrdemServicoMapper mapper;

	@Operation(summary = ApiConstants.ORDEM_SERVICO_LIST_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ApiConstants.ORDEM_SERVICO_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public List<OrdemServicoSimpleResponse> getAll() {
		List<OrdemServico> all = osService.getAll();
		return all.stream().map(os -> mapper.toSimpleResponse(os)).collect(Collectors.toList());
	}

	@Operation(summary = ApiConstants.ORDEM_SERVICO_GET_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ApiConstants.ORDEM_SERVICO_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("/{ordemServicoUid}")
	public OrdemServicoResponse get(@PathParam("ordemServicoUid") UUID ordemServicoUid) {
		return mapper.toResponse(osService.get(ordemServicoUid));
	}

	@Operation(summary = ApiConstants.ORDEM_SERVICO_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ApiConstants.ORDEM_SERVICO_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = OrdemServicoResponse.class))),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull OrdemServicoCreateRequest request) {
		OrdemServico os = osService.create(request.getClienteUid(), request.getTipoServicoUid(), request.getValor(),
				request.getContato(), request.getDescricao(), request.getObservacao(), request.getEntrada(),
				request.getAgendadoPara(), request.getEntrega());
		OrdemServicoResponse response = mapper.toResponse(os);
		return Response.status(Status.CREATED).entity(response).build();
	}

	@Operation(summary = ApiConstants.ORDEM_SERVICO_UPDATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ApiConstants.ORDEM_SERVICO_UPDATE_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("/{ordemServicoUid}/version/{version}")
	public OrdemServicoResponse update(@PathParam("ordemServicoUid") UUID ordemServicoUid,
			@PathParam("version") Integer version, @Valid @NotNull OrdemServicoUpdateRequest request) {
		OrdemServico os = osService.update(ordemServicoUid, version, request.getTipoServicoUid(), request.getContato(),
				request.getDescricao(), request.getObservacao(), request.getEntrada(), request.getAgendadoPara(),
				request.getEntrega());
		return mapper.toResponse(os);
	}

	@Operation(summary = ApiConstants.ORDEM_SERVICO_DEFINIR_CONCLUSAO_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ApiConstants.ORDEM_SERVICO_DEFINIR_CONCLUSAO_RESPONSE),
			@APIResponse(responseCode = "400", description = ApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PATCH
	@Path("/{ordemServicoUid}/version/{version}/conclusao")
	public OrdemServicoResponse definirConclusao(@PathParam("ordemServicoUid") UUID ordemServicoUid,
			@PathParam("version") Integer version, @Valid @NotNull OrdemServicoConclusaoRequest request) {
		OrdemServico os = osService.definirConclusao(ordemServicoUid, version, request.getConclusao(),
				request.getValor());
		return mapper.toResponse(os);
	}
}
