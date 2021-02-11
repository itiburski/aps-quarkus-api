package br.com.jitec.aps.cadastro.rest.exception;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.jitec.aps.cadastro.rest.payload.response.ErrorResponse;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	private static final String INTERNAL_SERVER_ERROR_DETAIL = "Ocorreu um erro ao processar a sua requisição";
	private static final String BAD_REQUEST_DETAIL = "Erro na requisição: %s";

	private static final Logger LOG = Logger.getLogger(GenericExceptionMapper.class);

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof ProcessingException) {
			String message = getExceptionMessage(exception);
			return handleException(exception, Status.BAD_REQUEST, String.format(BAD_REQUEST_DETAIL, message));
		}

		LOG.error("error", exception);
		return handleException(exception, Status.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_DETAIL);
	}

	private Response handleException(Exception exception, StatusType status, String detail) {
		ErrorResponse error = ErrorResponse.builder().withStatus(status.getStatusCode())
				.withTitle(status.getReasonPhrase()).withDetail(detail).withInstance(uriInfo.getRequestUri())
				.build();
		return Response.status(status).entity(error).build();
	}

	private String getExceptionMessage(Throwable exception) {
		if (exception.getCause() != null) {
			return getExceptionMessage(exception.getCause());
		}
		return exception.getMessage();
	}
}
