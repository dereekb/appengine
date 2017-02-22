package com.dereekb.gae.web.api.model.exception;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link ApiSafeRuntimeException} wrapping around a
 * {@link AtomicOperationException}.
 * 
 * @author dereekb
 *
 */
public class ApiAtomicOperationException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	private AtomicOperationException exception;

	public ApiAtomicOperationException(AtomicOperationException exception) {
		this.setException(exception);
	}

	public AtomicOperationException getException() {
		return this.exception;
	}

	public void setException(AtomicOperationException exception) {
		if (exception == null) {
			throw new IllegalArgumentException("exception cannot be null.");
		}

		this.exception = exception;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return null;
	}

}
