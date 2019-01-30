package com.dereekb.gae.model.general.people.website.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dereekb.gae.model.general.people.website.WebsiteAddressType;

public class WebsiteAddressTypeValidator
        implements ConstraintValidator<ValidWebsiteAddressType, Integer> {

	@Override
	public void initialize(ValidWebsiteAddressType constraintAnnotation) {}

	@Override
	public boolean isValid(Integer value,
	                       ConstraintValidatorContext context) {
		boolean valid = true;

		if (value != null) {
			WebsiteAddressType type = WebsiteAddressType.valueOf(value);
			valid = type.id == value;
		}

		return valid;
	}

}
