package com.dereekb.gae.server.auth.security.app.exception;

/**
 * Thrown when some security details are unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableAppLoginSecurityDetails extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableAppLoginSecurityDetails() {
		super();
	}

	public UnavailableAppLoginSecurityDetails(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableAppLoginSecurityDetails(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableAppLoginSecurityDetails(String message) {
		super(message);
	}

	public UnavailableAppLoginSecurityDetails(Throwable cause) {
		super(cause);
	}

}
