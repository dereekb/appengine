package com.dereekb.gae.model.extension.links.system.modification.exception;

/**
 * Abstract {@link Exception} thrown by {@link LinkModificationSystemInterface}
 * for invalid requests.
 * 
 * @author dereekb
 *
 */
public class InvalidLinkModificationSystemRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLinkModificationSystemRequestException() {
		super();
	}

	public InvalidLinkModificationSystemRequestException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidLinkModificationSystemRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLinkModificationSystemRequestException(String message) {
		super(message);
	}

	public InvalidLinkModificationSystemRequestException(Throwable cause) {
		super(cause);
	}

}
