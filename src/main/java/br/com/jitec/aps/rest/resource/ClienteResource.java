package br.com.jitec.aps.rest.resource;

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

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.api.ApiConstants;
import br.com.jitec.aps.business.service.ClienteService;
import br.com.jitec.aps.rest.payload.mapper.ClienteMapper;
import br.com.jitec.aps.rest.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.rest.payload.response.ClienteResponse;
import br.com.jitec.aps.rest.payload.response.ClienteSimplifResponse;
import br.com.jitec.aps.rest.validation.ValidationMessages;

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
	public ClienteResponse create(
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_NOT_NULL) ClienteCreateRequest request) {
		return mapper.toResponse(service.create(request.getCodigo(), request.getNome(), request.getRazaoSocial(), request.getContato(),
				request.getAtivo(), request.getRua(), request.getComplemento(), request.getBairro(), request.getCep(), request.getHomepage(),
				request.getCnpj(), request.getInscricaoEstadual(), request.getCidadeUid(), request.getCategoriaUid()));
	}

	// TODO
//	@PUT
	
	@DELETE
	@Path("{clienteUid}")
	public void delete(@PathParam UUID clienteUid) {
		service.delete(clienteUid);
	}
	
}
