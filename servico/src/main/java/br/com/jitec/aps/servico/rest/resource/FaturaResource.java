package br.com.jitec.aps.servico.rest.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import br.com.jitec.aps.servico.business.data.FaturaFilter;
import br.com.jitec.aps.servico.business.service.FaturaService;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.rest.payload.mapper.FaturaMapper;
import br.com.jitec.aps.servico.rest.payload.request.FaturaRequest;
import br.com.jitec.aps.servico.rest.payload.response.FaturaResponse;
import br.com.jitec.aps.servico.rest.payload.response.FaturaSimpleResponse;

@Tag(name = ServicoApiConstants.TAG_FATURAS)
@Path("/faturas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaturaResource {

	@Inject
	FaturaService service;

	@Inject
	FaturaMapper faturaMapper;

	@Operation(summary = ServicoApiConstants.FATURA_LIST_OPERATION, description = ServicoApiConstants.FATURA_LIST_OPERATION_DESCRIPTION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.FATURA_LIST_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	public Response getAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size,
			@QueryParam("clienteUid") UUID clienteUid, @QueryParam("codigo") Integer codigo,
			@QueryParam("dataFrom") LocalDate dataFrom, @QueryParam("dataTo") LocalDate dataTo) {

		Pagination pagination = Pagination.builder().withPage(page).withSize(size).build();
		FaturaFilter filter = new FaturaFilter(clienteUid, codigo, dataFrom, dataTo);

		Paged<Fatura> query = service.getAll(pagination, filter);
		List<FaturaSimpleResponse> faturasSimpleResponse = query.getContent().stream()
				.map(fatura -> faturaMapper.toSimpleResponse(fatura)).collect(Collectors.toList());

		return Response.ok(faturasSimpleResponse).header(Headers.PAGE_NUMBER, pagination.getPage())
				.header(Headers.PAGE_SIZE, pagination.getSize()).header(Headers.TOTAL_PAGES, query.getPageCount())
				.header(Headers.TOTAL_ITEMS, query.getItemCount()).build();
	}

	@Operation(summary = ServicoApiConstants.FATURA_GET_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "200", description = ServicoApiConstants.FATURA_GET_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@GET
	@Path("/{faturaUid}")
	public FaturaResponse get(@PathParam("faturaUid") UUID faturaUid) {
		Fatura fatura = service.getComplete(faturaUid);
		return faturaMapper.toResponse(fatura);
	}

	@Operation(summary = ServicoApiConstants.FATURA_CREATE_OPERATION)
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = ServicoApiConstants.FATURA_CREATE_RESPONSE, content = @Content(schema = @Schema(allOf = FaturaSimpleResponse.class))),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "422", description = ServicoApiConstants.STATUS_CODE_UNPROCESSABLE_ENTITY),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@POST
	public Response create(@Valid @NotNull FaturaRequest request) {
		Fatura fatura = service.create(request.getData(), request.getOrdensServicoUid());
		FaturaSimpleResponse response = faturaMapper.toSimpleResponse(fatura);

		return Response.status(Status.CREATED).entity(response).build();
	}

	@Operation(summary = ServicoApiConstants.FATURA_DELETE_OPERATION)
	@APIResponses(value = { @APIResponse(responseCode = "204", description = ServicoApiConstants.FATURA_DELETE_RESPONSE),
			@APIResponse(responseCode = "400", description = ServicoApiConstants.STATUS_CODE_BAD_REQUEST),
			@APIResponse(responseCode = "404", description = ServicoApiConstants.STATUS_CODE_NOT_FOUND),
			@APIResponse(responseCode = "500", description = ServicoApiConstants.STATUS_CODE_SERVER_ERROR) })
	@DELETE
	@Path("/{faturaUid}/version/{version}")
	public void delete(@PathParam("faturaUid") UUID faturaUid, @PathParam("version") Integer version) {
		service.delete(faturaUid, version);
	}

}
