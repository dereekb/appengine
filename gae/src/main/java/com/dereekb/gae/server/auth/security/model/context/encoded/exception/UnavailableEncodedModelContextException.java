package com.dereekb.gae.server.auth.security.model.context.encoded.exception;

import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;

/**
 * Thrown by {@link EncodedLoginTokenModelContextSet}.
 * 
 * @author dereekb
 *
 */
public class UnavailableEncodedModelContextException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableEncodedModelContextException() {
		super();
	}

	public UnavailableEncodedModelContextException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableEncodedModelContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableEncodedModelContextException(String message) {
		super(message);
	}

	public UnavailableEncodedModelContextException(Throwable cause) {
		super(cause);
	}

}
