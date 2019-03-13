package com.dereekb.gae.utilities.validation;

/**
 * Validator for an object's fields.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type
 */
public interface ContentValidator<T> {

	public void validateContent(T value) throws ContentValidationException;

}
