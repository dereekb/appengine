package com.dereekb.gae.web.api.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link ApiSafeRuntimeException} wrapper for
 * {@link UnsupportedOperationException}.
 * <p>
 * This should generally be used for instances where a request handler may or
 * may not be supported based on the configuration.
 * 
 * @author dereekb
 *
 */
public class ApiUnsupportedOperationException extends ApiSafeRuntimeException {

	public static final String ERROR_CODE = "METHOD_UNSUPPORTED";
	public static final String ERROR_TITLE = "Method Unsupported";

	private static final long serialVersionUID = 1L;

	private UnsupportedOperationException exception;

	public ApiUnsupportedOperationException(UnsupportedOperationException exception) {
		this.setException(exception);
	}

	public UnsupportedOperationException getException() {
		return this.exception;
	}

	public void setException(UnsupportedOperationException exception) {
		if (exception == null) {
			throw new IllegalArgumentException("Exception cannot be null.");
		}

		this.exception = exception;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		String detail = this.exception.getMessage();
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, detail);
	}

	@Override
	public String toString() {
		return "ApiUnsupportedOperationException [exception=" + this.exception + "]";
	}

}
