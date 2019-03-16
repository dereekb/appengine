package com.dereekb.gae.model.general.time.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dereekb.gae.model.general.time.WeekSpan;

/**
 * Validates an encoded {@link WeekSpan}.
 * 
 * @author dereekb
 *
 */
public class EncodedWeekSpanValidator
        implements ConstraintValidator<ValidEncodedWeekSpan, List<Integer>> {

	@Override
	public void initialize(ValidEncodedWeekSpan constraintAnnotation) {}

	@Override
	public boolean isValid(List<Integer> values,
	                       ConstraintValidatorContext context) {

		// TODO: Validate input week span values.

		return true;
	}

}
