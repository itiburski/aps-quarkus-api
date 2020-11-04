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
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.api.ApiConstants;
import br.com.jitec.aps.business.service.ClienteService;
import br.com.jitec.aps.rest.payload.mapper.ClienteMapper;
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

	@GET
	public List<ClienteSimplifResponse> getAll() {
		return mapper.toSimplifListResponse(service.getAll());
	}

	@GET
	@Path("{clienteUid}")
	public ClienteResponse get(@PathParam UUID clienteUid) {
		return mapper.toResponse(service.get(clienteUid));
	}

	@POST
	public ClienteResponse create(@Valid @NotNull ClienteCreateRequest request) {
		return mapper.toResponse(service.create(request.getCodigo(), request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getRua(), request.getComplemento(), request.getBairro(), request.getCep(),
				request.getHomepage(), request.getCnpj(), request.getInscricaoEstadual(), request.getCidadeUid(),
				request.getCategoriaUid()));
	}

	@PUT
	@Path("{clienteUid}")
	@Operation(summary = "Update all Cliente's fields with the payload values. If the payload value is empty or null, the field's value will be erased")
	public ClienteResponse updateAll(@PathParam UUID clienteUid, @Valid @NotNull ClienteUpdateRequest request) {
		return mapper.toResponse(service.updateAll(clienteUid, request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getAtivo(), request.getRua(), request.getComplemento(),
				request.getBairro(), request.getCep(), request.getHomepage(), request.getCnpj(),
				request.getInscricaoEstadual(), request.getCidadeUid(), request.getCategoriaUid()));
	}

	@PATCH
	@Path("{clienteUid}")
	@Operation(summary = "Update each Cliente's field only when the related payload field has a meaningful value (is not null). Otherwise, the field value will not be changed")
	public ClienteResponse updateNotNull(@PathParam UUID clienteUid, @Valid @NotNull ClienteUpdateRequest request) {
		return mapper.toResponse(service.updateNotNull(clienteUid, request.getNome(), request.getRazaoSocial(),
				request.getContato(), request.getAtivo(), request.getRua(), request.getComplemento(),
				request.getBairro(), request.getCep(), request.getHomepage(), request.getCnpj(),
				request.getInscricaoEstadual(), request.getCidadeUid(), request.getCategoriaUid()));
	}

	@DELETE
	@Path("{clienteUid}")
	public void delete(@PathParam UUID clienteUid) {
		service.delete(clienteUid);
	}

}
