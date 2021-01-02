package br.com.jitec.aps.rest.resource;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.api.ApiConstants;
import br.com.jitec.aps.business.data.ClienteEmailDTO;
import br.com.jitec.aps.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.business.service.ClienteService;
import br.com.jitec.aps.business.wrapper.Paged;
import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.rest.http.Pagination;
import br.com.jitec.aps.rest.payload.mapper.ClienteEmailMapper;
import br.com.jitec.aps.rest.payload.mapper.ClienteMapper;
import br.com.jitec.aps.rest.payload.mapper.ClienteTelefoneMapper;
import br.com.jitec.aps.rest.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.rest.payload.request.ClienteUpdateRequest;
import br.com.jitec.aps.rest.payload.response.ClienteResponse;
import br.com.jitec.aps.rest.payload.response.ClienteSimplifResponse;

@Tag(name = ApiConstants.TAG_CLIENTES)
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

	@Inject
	ClienteService service;

	@Inject
	ClienteMapper mapper;

	@Inject
	ClienteEmailMapper emailMapper;

	@Inject
	ClienteTelefoneMapper telefoneMapper;

	@GET
	public Response getClientes(@QueryParam("page") Integer page, @QueryParam("size") Integer size,
			@QueryParam("codigo") Integer codigo, @QueryParam("nomeOuRazaoSocial") String nomeOuRazaoSocial,
			@QueryParam("ativo") Boolean ativo, @QueryParam("sort") String sort) {

		Integer pageReq = Pagination.handlePage(page);
		Integer sizeReq = Pagination.handleSize(size);

		Paged<Cliente> query = service.getClientes((pageReq - 1), sizeReq, codigo, nomeOuRazaoSocial, ativo, sort);
		List<ClienteSimplifResponse> clientes = mapper.toSimplifListResponse(query.getContent());

		return Response.ok(clientes).header(Pagination.PAGE_NUMBER, pageReq).header(Pagination.PAGE_SIZE, sizeReq)
				.header(Pagination.TOTAL_PAGES, query.getPageCount())
				.header(Pagination.TOTAL_ITEMS, query.getItemCount()).build();
	}

	@GET
	@Path("{clienteUid}")
	public ClienteResponse get(@PathParam UUID clienteUid) {
		return mapper.toResponse(service.getComplete(clienteUid));
	}

	@POST
	public ClienteResponse create(@Valid @NotNull ClienteCreateRequest request) {
		List<ClienteEmailDTO> emails = emailMapper.toListDto(request.getEmails());
		List<ClienteTelefoneDTO> telefones = telefoneMapper.toListDto(request.getTelefones());

		return mapper.toResponse(service.create(request.getCodigo(), request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getRua(), request.getComplemento(), request.getBairro(), request.getCep(),
				request.getHomepage(), request.getCnpj(), request.getInscricaoEstadual(), request.getCidadeUid(),
				request.getCategoriaClienteUid(), emails, telefones));
	}

	@PUT
	@Path("{clienteUid}/version/{version}")
	@Operation(summary = "Update all Cliente's fields with the payload values. If the payload value is empty or null, the field's value will be erased")
	public ClienteResponse updateAll(@PathParam UUID clienteUid, @PathParam Integer version,
			@Valid @NotNull ClienteUpdateRequest request) {
		List<ClienteEmailDTO> emails = emailMapper.toListDto(request.getEmails());
		List<ClienteTelefoneDTO> telefones = telefoneMapper.toListDto(request.getTelefones());

		return mapper.toResponse(service.updateAll(clienteUid, version, request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getAtivo(), request.getRua(), request.getComplemento(),
				request.getBairro(), request.getCep(), request.getHomepage(), request.getCnpj(),
				request.getInscricaoEstadual(), request.getCidadeUid(), request.getCategoriaClienteUid(), emails,
				telefones));
	}

	@PATCH
	@Path("{clienteUid}/version/{version}")
	@Operation(summary = "Update each Cliente's field only when the related payload field has a meaningful value (is not null). Otherwise, the field value will not be changed")
	public ClienteResponse updateNotNull(@PathParam UUID clienteUid, @PathParam Integer version,
			@Valid @NotNull ClienteUpdateRequest request) {
		List<ClienteEmailDTO> emails = emailMapper.toListDto(request.getEmails());
		List<ClienteTelefoneDTO> telefones = telefoneMapper.toListDto(request.getTelefones());

		return mapper.toResponse(service.updateNotNull(clienteUid, version, request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getAtivo(), request.getRua(), request.getComplemento(),
				request.getBairro(), request.getCep(), request.getHomepage(), request.getCnpj(),
				request.getInscricaoEstadual(), request.getCidadeUid(), request.getCategoriaClienteUid(), emails,
				telefones));
	}

	@DELETE
	@Path("{clienteUid}/version/{version}")
	public void delete(@PathParam UUID clienteUid, @PathParam Integer version) {
		service.delete(clienteUid, version);
	}

}
