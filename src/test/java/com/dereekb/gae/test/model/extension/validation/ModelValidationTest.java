package com.dereekb.gae.test.model.extension.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import com.dereekb.gae.test.spring.CoreServiceTestingContext;

public abstract class ModelValidationTest extends CoreServiceTestingContext {

	protected Validator validator;

	protected BindingResult validate(Object target) {
		DataBinder binder = new DataBinder(target);
		binder.setValidator(validator);
		binder.validate();

		BindingResult results = binder.getBindingResult();
		return results;
	}

	protected boolean isInvalid(Object target) {
		BindingResult results = this.validate(target);
		return results.hasErrors();
	}

	protected boolean isValid(Object target) {
		return !this.isInvalid(target);
	}

	public Validator getValidator() {
		return validator;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
