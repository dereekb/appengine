package com.dereekb.gae.utilities.time.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when not enough time has passed for the function to execute again.
 * 
 * @author dereekb
 *
 */
public class RateLimitException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public RateLimitException() {
		super();
	}

	public RateLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RateLimitException(String message, Throwable cause) {
		super(message, cause);
	}

	public RateLimitException(String message) {
		super(message);
	}

	public RateLimitException(Throwable cause) {
		super(cause);
	}

	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl("RATE_LIMIT_EXCEPTION");
		error.setTitle("Rate Limited");
		error.setDetail(this.getMessage());
		return error;
	}

}
