package br.com.jitec.aps.commons.rest.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.jitec.aps.commons.business.exception.BusinessException;
import br.com.jitec.aps.commons.business.exception.ConstraintException;
import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.rest.exception.payload.ErrorResponse;
import br.com.jitec.aps.commons.rest.http.AdditionalStatus;

@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

	private Map<Class<? extends BusinessException>, StatusType> exceptionMapper;

	@Context
	private UriInfo uriInfo;

	public BusinessExceptionHandler() {
		exceptionMapper = new HashMap<>();
		exceptionMapper.put(DataNotFoundException.class, Status.NOT_FOUND);
		exceptionMapper.put(InvalidDataException.class, AdditionalStatus.UNPROCESSABLE_ENTITY);
		exceptionMapper.put(ConstraintException.class, Status.CONFLICT);
	}

	@Override
	public Response toResponse(BusinessException exception) {
		StatusType status = exceptionMapper.get(exception.getClass());
		ErrorResponse error = ErrorResponse.builder().withStatus(status.getStatusCode())
				.withTitle(status.getReasonPhrase()).withDetail(exception.getMessage())
				.withInstance(uriInfo.getRequestUri()).build();
		return Response.status(status).entity(error).build();
	}

}
