package com.dereekb.gae.server.mail.service.exception;

/**
 * {@link InvalidMailRequestException} thrown when the request has no recipients.
 * 
 * @author dereekb
 *
 */
public class NoBodyContentSenderMailRequestException extends InvalidMailRequestException {

	private static final long serialVersionUID = 1L;

	public NoBodyContentSenderMailRequestException() {
		super();
	}

	public NoBodyContentSenderMailRequestException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoBodyContentSenderMailRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoBodyContentSenderMailRequestException(String message) {
		super(message);
	}

	public NoBodyContentSenderMailRequestException(Throwable cause) {
		super(cause);
	}

}
