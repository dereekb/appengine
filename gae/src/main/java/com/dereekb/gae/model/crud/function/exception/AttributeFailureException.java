package com.dereekb.gae.model.crud.function.exception;

import com.dereekb.gae.model.crud.function.delegate.UpdateFunctionDelegate;

/**
 * Exception thrown when a change fails due to a field/attribute.
 *
 * @author dereekb
 * @see {@link UpdateFunctionDelegate}
 */
public final class AttributeFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String attribute;
	private final String value;
	private final String detail;

	public AttributeFailureException(String attribute, String value, String detail) {
		this.attribute = attribute;
		this.value = value;
		this.detail = detail;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public String getValue() {
		return this.value;
	}

	public String getDetail() {
		return this.detail;
	}

}
