package com.dereekb.gae.model.extension.links.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link LinkException} extension that provides a function for retrieving the
 * {@link ApiResponseError} associated with this type.
 *
 * @author dereekb
 *
 */
public abstract class ApiLinkException extends LinkException {

	private static final long serialVersionUID = 1L;

	public ApiLinkException() {
		super();
	}

	public ApiLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiLinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiLinkException(String message) {
		super(message);
	}

	public ApiLinkException(Throwable cause) {
		super(cause);
	}

	public abstract ApiResponseError getResponseError();

}
