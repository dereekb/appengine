package com.dereekb.gae.model.extension.links.components.system.exception;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;

/**
 * Thrown by {@link MutableLinkSystemBuilderEntry} with an unavailable type is requested.
 *
 * @author dereekb
 *
 */
public class UnregisteredLinkTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredLinkTypeException() {
		super();
	}

	public UnregisteredLinkTypeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnregisteredLinkTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnregisteredLinkTypeException(String message) {
		super(message);
	}

	public UnregisteredLinkTypeException(Throwable cause) {
		super(cause);
	}

}
