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
import br.com.jitec.aps.business.service.CategoriaClienteService;
import br.com.jitec.aps.rest.payload.mapper.CategoriaClienteMapper;
import br.com.jitec.aps.rest.payload.request.CategoriaClienteRequest;
import br.com.jitec.aps.rest.payload.response.CategoriaClienteResponse;
import br.com.jitec.aps.rest.validation.ValidationMessages;

@Tag(name = ApiConstants.TAG_CATEGORIAS_CLIENTE)
@Path("/categorias-cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaClienteResource {

	@Inject
	CategoriaClienteService service;

	@Inject
	CategoriaClienteMapper mapper;

	@GET
	public List<CategoriaClienteResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

	@GET
	@Path("{categClienteUid}")
	public CategoriaClienteResponse get(@PathParam UUID categClienteUid) {
		return mapper.toResponse(service.get(categClienteUid));
	}

	@POST
	public CategoriaClienteResponse create(
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_NOT_NULL) CategoriaClienteRequest request) {
		return mapper.toResponse(service.create(request.getDescricao()));
	}

	@PUT
	@Path("{categClienteUid}")
	public CategoriaClienteResponse update(@PathParam UUID categClienteUid,
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_NOT_NULL) CategoriaClienteRequest request) {
		return mapper.toResponse(service.update(categClienteUid, request.getDescricao()));
	}

	@DELETE
	@Path("{categClienteUid}")
	public void delete(@PathParam UUID categClienteUid) {
		service.delete(categClienteUid);
	}

}