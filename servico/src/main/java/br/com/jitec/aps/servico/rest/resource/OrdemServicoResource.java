package br.com.jitec.aps.servico.rest.resource;

import java.time.LocalDate;
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
import br.com.jitec.aps.servico.business.data.OrdemServicoFilter;
import br.com.jitec.aps.servico.business.service.OrdemServicoService;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.rest.payload.mapper.OrdemServicoMapper;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoLancamentoRequest;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoCreateRequest;
import br.com.jitec.aps.servico.rest.payload.request.OrdemServicoUpdateRequest;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoSimpleResponse;

@Tag(name = ServicoApiConstants.TAG_ORDENS_SERVICO)
@Path("/ordens-servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdemServicoResource {

	@Inject
	OrdemServicoService osService;

	@Inject
	OrdemServicoMapper mapper;

	@Operation(summary = ServicoApiConstants.ORDEM_SERVICO_LIST_OPERATION, description = ServicoApiConstants.ORDEM_SERVICO_LIST_OPERATION_DESCRIPTION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.ORDEM_SERVICO_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public Response getAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size,
			@QueryParam("clienteUid") UUID clienteUid, @QueryParam("entradaFrom") LocalDate entradaFrom,
			@QueryParam("entradaTo") LocalDate entradaTo, @QueryParam("entregue") Boolean entregue,
			@QueryParam("lancado") Boolean lancado, @QueryParam("faturado") Boolean faturado) {

		Pagination pagination = Pagination.builder().withPage(page).withSize(size).build();
		OrdemServicoFilter filter = OrdemServicoFilter.builder().withClienteUid(clienteUid).withEntradaFrom(entradaFrom)
				.withEntradaTo(entradaTo).withLancado(lancado).withEntregue(entregue).withFaturado(faturado).build();

		Paged<OrdemServico> query = osService.getAll(pagination, filter);
		List<OrdemServicoSimpleResponse> ordensServico = query.getContent().stream()
				.map(os -> mapper.toSimpleResponse(os)).collect(Collectors.toList());

		return Response.ok(ordensServico).header(Headers.PAGE_NUMBER, pagination.getPage())
				.header(Headers.PAGE_SIZE, pagination.getSize()).header(Headers.TOTAL_PAGES, query.getPageCount())
				.header(Headers.TOTAL_ITEMS, query.getItemCount()).build();
	}

	@Operation(summary = ServicoApiConstants.ORDEM_SERVICO_GET_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.ORDEM_SERVICO_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("/{ordemServicoUid}")
	public OrdemServicoResponse get(@PathParam("ordemServicoUid") UUID ordemServicoUid) {
		return mapper.toResponse(osService.get(ordemServicoUid));
	}

	@Operation(summary = ServicoApiConstants.ORDEM_SERVICO_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ServicoApiConstants.ORDEM_SERVICO_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = OrdemServicoResponse.class))),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull OrdemServicoCreateRequest request) {
		OrdemServico os = osService.create(request.getClienteUid(), request.getTipoServicoUid(), request.getValor(),
				request.getContato(), request.getDescricao(), request.getObservacao(), request.getEntrada(),
				request.getAgendadoPara(), request.getEntrega());
		OrdemServicoResponse response = mapper.toResponse(os);
		return Response.status(Status.CREATED).entity(response).build();
	}

	@Operation(summary = ServicoApiConstants.ORDEM_SERVICO_UPDATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ServicoApiConstants.ORDEM_SERVICO_UPDATE_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PUT
	@Path("/{ordemServicoUid}/version/{version}")
	public OrdemServicoResponse update(@PathParam("ordemServicoUid") UUID ordemServicoUid,
			@PathParam("version") Integer version, @Valid @NotNull OrdemServicoUpdateRequest request) {
		OrdemServico os = osService.update(ordemServicoUid, version, request.getTipoServicoUid(), request.getContato(),
				request.getDescricao(), request.getObservacao(), request.getEntrada(), request.getAgendadoPara(),
				request.getEntrega());
		return mapper.toResponse(os);
	}

	@Operation(summary = ServicoApiConstants.ORDEM_SERVICO_DEFINIR_LANCAMENTO_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = ServicoApiConstants.ORDEM_SERVICO_DEFINIR_LANCAMENTO_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@PATCH
	@Path("/{ordemServicoUid}/version/{version}/lancamento")
	public OrdemServicoResponse definirLancamento(@PathParam("ordemServicoUid") UUID ordemServicoUid,
			@PathParam("version") Integer version, @Valid @NotNull OrdemServicoLancamentoRequest request) {
		OrdemServico os = osService.definirLancamento(ordemServicoUid, version, request.getLancamento(),
				request.getValor());
		return mapper.toResponse(os);
	}
}
