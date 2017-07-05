package com.dereekb.gae.model.extension.links.system.components.exceptions;

/**
 * Runtime exception used for dynamic links.
 * 
 * @author dereekb
 *
 */
public class DynamicLinkInfoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DynamicLinkInfoException() {
		super();
	}

	public DynamicLinkInfoException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DynamicLinkInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DynamicLinkInfoException(String message) {
		super(message);
	}

	public DynamicLinkInfoException(Throwable cause) {
		super(cause);
	}

}
