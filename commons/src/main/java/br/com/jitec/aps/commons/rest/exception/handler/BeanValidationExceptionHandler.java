package br.com.jitec.aps.commons.rest.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.internal.engine.path.PathImpl;

import br.com.jitec.aps.commons.rest.exception.payload.ErrorResponse;
import br.com.jitec.aps.commons.rest.exception.payload.ViolationResponse;
import br.com.jitec.aps.commons.rest.http.AdditionalStatus;

@Provider
public class BeanValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

	private static final StatusType STATUS = AdditionalStatus.UNPROCESSABLE_ENTITY;
	private static final String CONSTRAINT_VIOLATION_DETAIL = "Parametros da requisição inválidos";

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		List<ViolationResponse> violations = exception.getConstraintViolations().stream()
				.map(constraint -> new ViolationResponse(
						((PathImpl) constraint.getPropertyPath()).getLeafNode().getName(), constraint.getMessage()))
				.collect(Collectors.toList());

		ErrorResponse error = ErrorResponse.builder().withStatus(STATUS.getStatusCode())
				.withTitle(STATUS.getReasonPhrase()).withDetail(CONSTRAINT_VIOLATION_DETAIL)
				.withInstance(uriInfo.getRequestUri()).withViolations(violations).build();
		return Response.status(STATUS).entity(error).build();
	}

}
