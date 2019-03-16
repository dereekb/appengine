package com.dereekb.gae.utilities.factory.exception;

import com.dereekb.gae.utilities.factory.Factory;

/**
 * Runtime exception thrown when a {@link Factory} cannot complete the make()
 * function.
 *
 * @author dereekb
 */
public class FactoryMakeFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FactoryMakeFailureException() {}

	public FactoryMakeFailureException(String string) {
		super(string);
	}

	public FactoryMakeFailureException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FactoryMakeFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public FactoryMakeFailureException(Throwable cause) {
		super(cause);
	}

}
