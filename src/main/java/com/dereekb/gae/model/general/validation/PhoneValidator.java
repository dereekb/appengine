package com.dereekb.gae.model.general.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneValidator
        implements ConstraintValidator<Phone, String> {

	boolean fastCheck;

	@Override
	public void initialize(Phone constraintAnnotation) {
		fastCheck = constraintAnnotation.fast();
	}

	@Override
	public boolean isValid(String number,
	                       ConstraintValidatorContext context) {
		boolean isValid = true;

		if (number != null) {
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			PhoneNumber phoneNumber = null;

			try {
				phoneNumber = phoneUtil.parse(number, "US");

				if (fastCheck) {
					isValid = phoneUtil.isPossibleNumber(phoneNumber);
				} else {
					isValid = phoneUtil.isValidNumber(phoneNumber);
				}
			} catch (NumberParseException e) {
				isValid = false;
			}
		}

		return isValid;
	}

}