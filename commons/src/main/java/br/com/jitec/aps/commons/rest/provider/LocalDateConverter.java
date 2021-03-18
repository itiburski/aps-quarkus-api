package br.com.jitec.aps.commons.rest.provider;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;

/**
 * Converts an QueryParam parameter to LocalDate type
 */
public class LocalDateConverter implements ParamConverter<LocalDate> {

	@Override
	public LocalDate fromString(String value) {
		if (value == null)
			return null;
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException e) {
			throw new BadRequestException(e);
		}
	}

	@Override
	public String toString(LocalDate value) {
		if (value == null)
			return null;
		try {
			return value.toString();
		} catch (DateTimeParseException e) {
			throw new BadRequestException(e);
		}
	}

}
