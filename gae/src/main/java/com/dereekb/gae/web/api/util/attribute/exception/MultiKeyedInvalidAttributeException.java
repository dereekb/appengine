package com.dereekb.gae.web.api.util.attribute.exception;

import java.util.List;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;

/**
 * Exception that contains one or more {@link KeyedInvalidAttribute}
 * instances.
 * 
 * @author dereekb
 * 
 * @see KeyedInvalidAttributeException
 */
public class MultiKeyedInvalidAttributeException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	private List<? extends KeyedInvalidAttribute> failures;

	public MultiKeyedInvalidAttributeException(List<? extends KeyedInvalidAttribute> failures) {
		this.setFailures(failures);
	}

	public List<? extends KeyedInvalidAttribute> getFailures() {
		return this.failures;
	}

	public void setFailures(List<? extends KeyedInvalidAttribute> failures) {
		if (failures == null) {
			throw new IllegalArgumentException("Failures cannot be null.");
		}

		this.failures = failures;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return KeyedInvalidAttributeApiResponseBuilder.make(this.failures);
	}

}
