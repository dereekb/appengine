package com.dereekb.gae.web.api.shared.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Thrown when an argument is unusable.
 *
 * Generally used for other internal-argument validation situations.
 *
 * @author dereekb
 * 
 */
@Deprecated
public final class RequestArgumentException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Argument of the input request.
	 */
	private final String argument;

	/**
	 * Detailed reason.
	 */
	private final String detail;

	public RequestArgumentException(String argument, String detail) {
		this.argument = argument;
		this.detail = detail;
	}

	public String getArgument() {
		return this.argument;
	}

	public String getDetail() {
		return this.detail;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		// TODO Auto-generated method stub
		return null;
	}

}
