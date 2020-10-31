package br.com.jitec.aps.rest.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.jitec.aps.rest.payload.response.ErrorResponse;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	private static final String GENERIC_MESSAGE = "Ocorreu um erro ao processar a sua requisição";
	private static final StatusType STATUS = Status.INTERNAL_SERVER_ERROR;

	private static final Logger LOG = Logger.getLogger(GenericExceptionMapper.class);

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(Exception exception) {
		LOG.error("error", exception);
		ErrorResponse error = ErrorResponse.builder().withStatus(STATUS.getStatusCode())
				.withTitle(STATUS.getReasonPhrase()).withDetail(GENERIC_MESSAGE)
				.withInstance(uriInfo.getRequestUri()).build();
		return Response.status(STATUS).entity(error).build();
	}
}
