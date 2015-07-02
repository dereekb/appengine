package com.dereekb.gae.model.extension.data.conversion.exception;

/**
 * Thrown when a model conversion fails.
 *
 * @author dereekb
 */
public final class ConversionFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConversionFailureException() {
		super();
	}

	public ConversionFailureException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConversionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversionFailureException(String message) {
		super(message);
	}

	public ConversionFailureException(Throwable cause) {
		super(cause);
	}

}
