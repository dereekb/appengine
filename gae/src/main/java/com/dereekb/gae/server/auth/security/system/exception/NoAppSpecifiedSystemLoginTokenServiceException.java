package com.dereekb.gae.server.auth.security.system.exception;

/**
 * {@link SystemLoginTokenServiceException} extension for when the app name is
 * not specified.
 *
 * @author dereekb
 *
 */
public class NoAppSpecifiedSystemLoginTokenServiceException extends SystemLoginTokenServiceException {

	private static final long serialVersionUID = 1L;

	public NoAppSpecifiedSystemLoginTokenServiceException() {
		super();
	}

	public NoAppSpecifiedSystemLoginTokenServiceException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoAppSpecifiedSystemLoginTokenServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAppSpecifiedSystemLoginTokenServiceException(String message) {
		super(message);
	}

	public NoAppSpecifiedSystemLoginTokenServiceException(Throwable cause) {
		super(cause);
	}

}
