package com.dereekb.gae.server.datastore.models.keys.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Exception for instances where {@link ModelKey} is null when it should not.
 *
 * @author dereekb
 *
 */
public class NullModelKeyException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NullModelKeyException() {
		super();
	}

	public NullModelKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullModelKeyException(String s) {
		super(s);
	}

	public NullModelKeyException(Throwable cause) {
		super(cause);
	}

}
