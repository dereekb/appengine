package com.dereekb.gae.model.extension.links.components.system.exception;

/**
 * Thrown if the requested links or changes are unrelated to each other.
 *
 * @author dereekb
 *
 */
public class UnrelatedLinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnrelatedLinkException() {
		super();
	}

	public UnrelatedLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnrelatedLinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnrelatedLinkException(String message) {
		super(message);
	}

	public UnrelatedLinkException(Throwable cause) {
		super(cause);
	}

}
