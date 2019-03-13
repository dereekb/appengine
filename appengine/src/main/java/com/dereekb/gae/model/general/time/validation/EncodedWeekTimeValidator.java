package com.dereekb.gae.model.general.time.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author dereekb
 *
 */
public class EncodedWeekTimeValidator
        implements ConstraintValidator<ValidEncodedWeekTime, Integer> {

	@Override
	public void initialize(ValidEncodedWeekTime constraintAnnotation) {}

	@Override
	public boolean isValid(Integer value,
	                       ConstraintValidatorContext context) {

		// TODO: Validate Week Time. Must have days specified, or time.

		return true;
	}

}
