package com.dereekb.gae.utilities.query.exception;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * More specific {@link IllegalArgumentException} for queries cases.
 *
 * @author dereekb
 *
 * @see InvalidAttributeException for invalid fields/values within queries.
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
