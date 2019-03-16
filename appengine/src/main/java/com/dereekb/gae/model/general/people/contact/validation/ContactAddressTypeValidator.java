package com.dereekb.gae.model.general.people.contact.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dereekb.gae.model.general.people.contact.ContactAddressType;

public class ContactAddressTypeValidator
        implements ConstraintValidator<ValidContactAddressType, Integer> {

	@Override
	public void initialize(ValidContactAddressType constraintAnnotation) {}

	@Override
	public boolean isValid(Integer value,
	                       ConstraintValidatorContext context) {
		boolean valid = true;

		if (value != null) {
			ContactAddressType type = ContactAddressType.valueOf(value);
			valid = type.id == value;
		}

		return valid;
	}

}
