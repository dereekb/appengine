package com.dereekb.gae.utilities.model.search.exception;

import com.dereekb.gae.utilities.model.search.response.ModelSearchResponse;

/**
 * Thrown when models are requested out of a keys-only response.
 * 
 * @author dereekb
 * 
 * @see ModelSearchResponse
 */
public class KeysOnlySearchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public KeysOnlySearchException() {
		super();
	}

	public KeysOnlySearchException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KeysOnlySearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeysOnlySearchException(String message) {
		super(message);
	}

	public KeysOnlySearchException(Throwable cause) {
		super(cause);
	}

}
