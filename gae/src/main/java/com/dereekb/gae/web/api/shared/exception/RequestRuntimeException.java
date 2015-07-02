package com.dereekb.gae.web.api.shared.exception;

/**
 * Thrown when an API call encounters an unexpected RuntimeException.
 *
 * @author dereekb
 *
 */
@Deprecated
public final class RequestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestRuntimeException(Throwable cause) {
		super(cause);
	}

}
