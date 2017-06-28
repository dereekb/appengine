package com.dereekb.gae.web.api.util.attribute.exception;

import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.InvalidAttributeImpl;

/**
 * Exception thrown when a change fails due to a field/attribute.
 *
 * @author dereekb
 * 
 * @see InvalidAttribute
 */
public class InvalidAttributeException extends ApiSafeRuntimeException
        implements InvalidAttribute {

	private static final long serialVersionUID = 1L;

	private final InvalidAttribute failure;

	public InvalidAttributeException(String attribute, String value, String detail) {
		this.failure = new InvalidAttributeImpl(attribute, value, detail);
	}

	protected InvalidAttributeException(InvalidAttribute failure) {
		this.failure = failure;
	}

	public InvalidAttribute getFailure() {
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
	public ErrorInfo getError() {
		return this.failure.getError();
	}

	@Override
	public String toString() {
		return "AttributeFailureException [failure=" + this.failure + "]";
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return KeyedInvalidAttributeApiResponseBuilder.make(this);
	}

}
