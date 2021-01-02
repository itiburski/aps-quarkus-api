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
import br.com.jitec.aps.business.service.CidadeService;
import br.com.jitec.aps.rest.payload.mapper.CidadeMapper;
import br.com.jitec.aps.rest.payload.request.CidadeRequest;
import br.com.jitec.aps.rest.payload.response.CidadeResponse;

@Tag(name = ApiConstants.TAG_CIDADES)
@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CidadeResource {

	@Inject
	CidadeService service;

	@Inject
	CidadeMapper mapper;

	@GET
	public List<CidadeResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

	@GET
	@Path("{cidadeUid}")
	public CidadeResponse get(@PathParam UUID cidadeUid) {
		return mapper.toResponse(service.get(cidadeUid));
	}

	@POST
	public CidadeResponse create(@Valid @NotNull CidadeRequest request) {
		return mapper.toResponse(service.create(request.getNome(), request.getUf()));
	}

	@PUT
	@Path("{cidadeUid}/version/{version}")
	public CidadeResponse update(@PathParam UUID cidadeUid, @PathParam Integer version,
			@Valid @NotNull CidadeRequest request) {
		return mapper.toResponse(service.update(cidadeUid, version, request.getNome(), request.getUf()));
	}

	@DELETE
	@Path("{cidadeUid}/version/{version}")
	public void delete(@PathParam UUID cidadeUid, @PathParam Integer version) {
		service.delete(cidadeUid, version);
	}

}
