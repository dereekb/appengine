package com.dereekb.gae.model.extension.links.system.components.exceptions;

import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;

/**
 * {@link LinkSystemException} that implements {@link ApiResponseErrorConvertable}.
 * 
 * @author dereekb
 *
 */
public abstract class ApiLinkSystemException extends LinkSystemException 
	implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	public ApiLinkSystemException() {
		super();
	}

	public ApiLinkSystemException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiLinkSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiLinkSystemException(String message) {
		super(message);
	}

	public ApiLinkSystemException(Throwable cause) {
		super(cause);
	}

}
