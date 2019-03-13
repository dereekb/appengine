package com.dereekb.gae.web.api.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Used to wrap a {@link ApiResponseErrorConvertable}.
 * 
 * @author dereekb
 *
 */
public class WrappedApiErrorException extends ApiSafeRuntimeException
        implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	private ApiResponseErrorConvertable error;

	public WrappedApiErrorException(ApiResponseErrorConvertable error) {
		this.setError(error);
	}

	protected ApiResponseErrorConvertable getError() {
		return this.error;
	}

	protected void setError(ApiResponseErrorConvertable error) {
		if (error == null) {
			throw new IllegalArgumentException("Error cannot be null.");
		}

		this.error = error;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return this.error.asResponseError();
	}

}
