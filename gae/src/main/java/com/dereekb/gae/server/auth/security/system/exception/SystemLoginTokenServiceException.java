package com.dereekb.gae.server.auth.security.system.exception;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenService;

/**
 * {@link RuntimeException} for {@link SystemLoginTokenService} when a request
 * encounters an issue.
 *
 * @author dereekb
 *
 */
public class SystemLoginTokenServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemLoginTokenServiceException() {
		super();
	}

	public SystemLoginTokenServiceException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemLoginTokenServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemLoginTokenServiceException(String message) {
		super(message);
	}

	public SystemLoginTokenServiceException(Throwable cause) {
		super(cause);
	}

}
