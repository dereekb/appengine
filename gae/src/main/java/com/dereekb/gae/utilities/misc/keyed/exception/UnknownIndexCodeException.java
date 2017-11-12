package com.dereekb.gae.utilities.misc.keyed.exception;

import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * Thrown in an {@link IndexCoded} context where the supplied value was not
 * indexed.
 * 
 * @author dereekb
 *
 */
public class UnknownIndexCodeException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public UnknownIndexCodeException() {
		super();
	}

	public UnknownIndexCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownIndexCodeException(String s) {
		super(s);
	}

	public UnknownIndexCodeException(Throwable cause) {
		super(cause);
	}

}
