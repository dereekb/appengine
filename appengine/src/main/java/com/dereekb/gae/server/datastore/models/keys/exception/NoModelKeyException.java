package com.dereekb.gae.server.datastore.models.keys.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Exception for instances where a {@link ModelKey} is unavailable.
 * 
 * @author dereekb
 *
 */
public class NoModelKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoModelKeyException() {
		super();
	}

	public NoModelKeyException(String s) {
		super(s);
	}

	public NoModelKeyException(Throwable cause) {
		super(cause);
	}

}
