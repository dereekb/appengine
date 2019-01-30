package com.dereekb.gae.utilities.misc.keyed.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Exception for instances where {@link ModelKey} is null when it should not.
 *
 * @author dereekb
 *
 */
public class NullKeyException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NullKeyException() {
		super();
	}

	public NullKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullKeyException(String s) {
		super(s);
	}

	public NullKeyException(Throwable cause) {
		super(cause);
	}

}
