package br.com.jitec.aps.rest.exception;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.jitec.aps.rest.http.AdditionalStatus;
import br.com.jitec.aps.rest.payload.response.ErrorResponse;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	private static final StatusType STATUS = AdditionalStatus.UNPROCESSABLE_ENTITY;

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		String violations = exception.getConstraintViolations().stream().map(constraint -> constraint.getMessage())
				.collect(Collectors.joining(", "));

		ErrorResponse error = ErrorResponse.builder().withStatus(STATUS.getStatusCode())
				.withTitle(STATUS.getReasonPhrase()).withDetail(violations)
				.withInstance(uriInfo.getRequestUri()).build();
		return Response.status(STATUS).entity(error).build();
	}

}
