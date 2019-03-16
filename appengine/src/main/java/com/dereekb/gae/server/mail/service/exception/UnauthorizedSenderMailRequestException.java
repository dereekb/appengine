package com.dereekb.gae.server.mail.service.exception;

/**
 * {@link InvalidMailRequestException} thrown when the sender on a request is
 * not authorized.
 * 
 * @author dereekb
 *
 */
public class UnauthorizedSenderMailRequestException extends InvalidMailRequestException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedSenderMailRequestException() {
		super();
	}

	public UnauthorizedSenderMailRequestException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnauthorizedSenderMailRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedSenderMailRequestException(String message) {
		super(message);
	}

	public UnauthorizedSenderMailRequestException(Throwable cause) {
		super(cause);
	}

}
