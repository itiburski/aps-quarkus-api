package br.com.jitec.aps.servico.rest.resource;

import java.time.LocalDate;
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
import javax.ws.rs.PathParam;
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

import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.rest.http.Headers;
import br.com.jitec.aps.servico.api.ServicoApiConstants;
import br.com.jitec.aps.servico.business.data.BaixaFilter;
import br.com.jitec.aps.servico.business.service.BaixaService;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.payload.mapper.BaixaMapper;
import br.com.jitec.aps.servico.payload.request.BaixaCreateRequest;
import br.com.jitec.aps.servico.payload.request.BaixaUpdateRequest;
import br.com.jitec.aps.servico.payload.response.BaixaResponse;
import br.com.jitec.aps.servico.payload.response.BaixaSlimResponse;

@Tag(name = ServicoApiConstants.TAG_BAIXAS)
@Path("/baixas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaixaResource {

	@Inject
	BaixaService service;

	@Inject
	BaixaMapper mapper;

	@Operation(summary = ServicoApiConstants.BAIXA_LIST_OPERATION, description = ServicoApiConstants.BAIXA_LIST_OPERATION_DESCRIPTION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.BAIXA_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public Response getAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size,
			@QueryParam("clienteUid") UUID clienteUid, @QueryParam("tipoBaixaUid") UUID tipoBaixaUid,
			@QueryParam("dataFrom") LocalDate dataFrom, @QueryParam("dataTo") LocalDate dataTo) {

		Pagination pagination = Pagination.builder().withPage(page).withSize(size).build();
		BaixaFilter filter = new BaixaFilter(clienteUid, tipoBaixaUid, dataFrom, dataTo);

		Paged<Baixa> query = service.getAll(pagination, filter);
		List<BaixaSlimResponse> baixaSimpleResponse = mapper.toListSlimResponse(query.getContent());

		return Response.ok(baixaSimpleResponse).header(Headers.PAGE_NUMBER, pagination.getPage())
				.header(Headers.PAGE_SIZE, pagination.getSize()).header(Headers.TOTAL_PAGES, query.getPageCount())
				.header(Headers.TOTAL_ITEMS, query.getItemCount()).build();
	}

	@Operation(summary = ServicoApiConstants.BAIXA_GET_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.BAIXA_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("/{baixaUid}")
	public BaixaResponse get(@PathParam("baixaUid") UUID baixaUid) {
		Baixa baixa = service.get(baixaUid);
		return mapper.toResponse(baixa);
	}

	@Operation(summary = ServicoApiConstants.BAIXA_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ServicoApiConstants.BAIXA_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = BaixaSlimResponse.class))),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull BaixaCreateRequest request) {
		Baixa baixa = service.create(request.getTipoBaixaUid(), request.getData(), request.getValor(),
				request.getObservacao(), request.getClienteUid());
		BaixaResponse response = mapper.toResponse(baixa);
		return Response.status(Status.CREATED).entity(response).build();
	}

	@Operation(summary = ServicoApiConstants.BAIXA_UPDATE_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.BAIXA_UPDATE_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("/{baixaUid}/version/{version}")
	public BaixaResponse update(@PathParam("baixaUid") UUID baixaUid, @PathParam("version") Integer version,
			@Valid @NotNull BaixaUpdateRequest request) {
		Baixa baixa = service.update(baixaUid, version, request.getTipoBaixaUid(), request.getData(),
				request.getValor(), request.getObservacao());
		return mapper.toResponse(baixa);
	}

	@Operation(summary = ServicoApiConstants.BAIXA_DELETE_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "204", description = ServicoApiConstants.BAIXA_DELETE_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@DELETE
	@Path("/{baixaUid}/version/{version}")
	public void delete(@PathParam("baixaUid") UUID baixaUid, @PathParam("version") Integer version) {
		service.delete(baixaUid, version);
	}

}
