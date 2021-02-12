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

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.cadastro.api.ApiConstants;
import br.com.jitec.aps.cadastro.business.service.CategoriaClienteService;
import br.com.jitec.aps.cadastro.rest.payload.mapper.CategoriaClienteMapper;
import br.com.jitec.aps.cadastro.rest.payload.request.CategoriaClienteRequest;
import br.com.jitec.aps.cadastro.rest.payload.response.CategoriaClienteResponse;

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
	@Path("{categoriaClienteUid}")
	public CategoriaClienteResponse get(@PathParam UUID categoriaClienteUid) {
		return mapper.toResponse(service.get(categoriaClienteUid));
	}

	@POST
	public CategoriaClienteResponse create(@Valid @NotNull CategoriaClienteRequest request) {
		return mapper.toResponse(service.create(request.getDescricao()));
	}

	@PUT
	@Path("{categoriaClienteUid}/version/{version}")
	public CategoriaClienteResponse update(@PathParam UUID categoriaClienteUid, @PathParam Integer version,
			@Valid @NotNull CategoriaClienteRequest request) {
		return mapper.toResponse(service.update(categoriaClienteUid, version, request.getDescricao()));
	}

	@DELETE
	@Path("{categoriaClienteUid}/version/{version}")
	public void delete(@PathParam UUID categoriaClienteUid, @PathParam Integer version) {
		service.delete(categoriaClienteUid, version);
	}

}