package com.dereekb.gae.utilities.query.exception;

/**
 * More specific {@link IllegalArgumentException} for queries.
 * 
 * @author dereekb
 *
 */
public class IllegalQueryArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalQueryArgumentException() {
		super();
	}

	public IllegalQueryArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalQueryArgumentException(String s) {
		super(s);
	}

	public IllegalQueryArgumentException(Throwable cause) {
		super(cause);
	}

}
