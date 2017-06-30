package com.dereekb.gae.model.extension.links.system.exception;

/**
 * 
 * @author dereekb
 *
 */
public class UnavailableLinkModelAccessorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableLinkModelAccessorException() {
		super();
	}

	public UnavailableLinkModelAccessorException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableLinkModelAccessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableLinkModelAccessorException(String message) {
		super(message);
	}

	public UnavailableLinkModelAccessorException(Throwable cause) {
		super(cause);
	}

}
