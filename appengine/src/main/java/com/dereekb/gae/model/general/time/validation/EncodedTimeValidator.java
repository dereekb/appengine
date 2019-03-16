package com.dereekb.gae.model.general.time.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dereekb.gae.model.general.time.Time;

public class EncodedTimeValidator
        implements ConstraintValidator<ValidEncodedTime, Integer> {

	@Override
	public void initialize(ValidEncodedTime constraintAnnotation) {}

	@Override
	public boolean isValid(Integer value,
	                       ConstraintValidatorContext context) {
		boolean valid = true;

		if (value != null) {
			valid = value >= 0 && value <= Time.MAX_TIME;
		}

		return valid;
	}

}
