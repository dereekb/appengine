package com.dereekb.gae.model.crud.exception;

import com.dereekb.gae.model.crud.util.AttributeUpdateFailure;
import com.dereekb.gae.model.crud.util.impl.AttributeUpdateFailureImpl;

/**
 * Exception thrown when a change fails due to a field/attribute.
 *
 * @author dereekb
 * 
 * @see AttributeUpdateFailure
 */
public class AttributeFailureException extends RuntimeException
        implements AttributeUpdateFailure {

	private static final long serialVersionUID = 1L;

	private final AttributeUpdateFailure failure;

	public AttributeFailureException(String attribute, String value, String detail) {
		this.failure = new AttributeUpdateFailureImpl(attribute, value, detail);
	}

	public AttributeUpdateFailure getFailure() {
		return this.failure;
	}

	@Override
	public String getAttribute() {
		return this.failure.getAttribute();
	}

	@Override
	public String getValue() {
		return this.failure.getValue();
	}

	@Override
	public String getDetail() {
		return this.failure.getDetail();
	}

	@Override
	public String toString() {
		return "AttributeFailureException [failure=" + this.failure + "]";
	}

}
