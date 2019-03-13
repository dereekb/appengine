package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

/**
 * 
 * @author dereekb
 *
 */
public class InternalLinkModificationSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalLinkModificationSystemException() {
		super();
	}

	public InternalLinkModificationSystemException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InternalLinkModificationSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalLinkModificationSystemException(String message) {
		super(message);
	}

	public InternalLinkModificationSystemException(Throwable cause) {
		super(cause);
	}

}
