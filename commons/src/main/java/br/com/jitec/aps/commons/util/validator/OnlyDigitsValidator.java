package br.com.jitec.aps.commons.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyDigitsValidator implements ConstraintValidator<OnlyDigits, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		String regex = "^[0-9]*$";
		return value.matches(regex);
	}

}
