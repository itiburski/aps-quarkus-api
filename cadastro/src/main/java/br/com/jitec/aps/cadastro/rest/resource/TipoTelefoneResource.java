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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.jitec.aps.cadastro.api.ApiConstants;
import br.com.jitec.aps.cadastro.business.service.TipoTelefoneService;
import br.com.jitec.aps.cadastro.rest.payload.mapper.TipoTelefoneMapper;
import br.com.jitec.aps.cadastro.rest.payload.request.TipoTelefoneRequest;
import br.com.jitec.aps.cadastro.rest.payload.response.TipoTelefoneResponse;

@Tag(name = ApiConstants.TAG_TIPO_TELEFONE)
@Path("/tipos-telefone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoTelefoneResource {

	@Inject
	TipoTelefoneService service;

	@Inject
	TipoTelefoneMapper mapper;

	@GET
	public List<TipoTelefoneResponse> getAll() {
		return mapper.toListResponse(service.getAll());
	}

	@GET
	@Path("{tipoTelefoneUid}")
	public TipoTelefoneResponse get(@PathParam UUID tipoTelefoneUid) {
		return mapper.toResponse(service.get(tipoTelefoneUid));
	}

	@POST
	public Response create(@Valid @NotNull TipoTelefoneRequest request) {
		TipoTelefoneResponse tipoTelefoneResponse = mapper.toResponse(service.create(request.getDescricao()));
		return Response.status(Status.CREATED).entity(tipoTelefoneResponse).build();
	}

	@PUT
	@Path("{tipoTelefoneUid}/version/{version}")
	public TipoTelefoneResponse update(@PathParam UUID tipoTelefoneUid, @PathParam Integer version,
			@Valid @NotNull TipoTelefoneRequest request) {
		return mapper.toResponse(service.update(tipoTelefoneUid, version, request.getDescricao()));
	}

	@DELETE
	@Path("{tipoTelefoneUid}/version/{version}")
	public void delete(@PathParam UUID tipoTelefoneUid, @PathParam Integer version) {
		service.delete(tipoTelefoneUid, version);
	}

}
