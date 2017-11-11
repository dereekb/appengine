package com.dereekb.gae.server.mail.service.exception;

/**
 * {@link InvalidMailRequestException} thrown when the request has no recipients.
 * 
 * @author dereekb
 *
 */
public class NoRecipientsSenderMailRequestException extends InvalidMailRequestException {

	private static final long serialVersionUID = 1L;

	public NoRecipientsSenderMailRequestException() {
		super();
	}

	public NoRecipientsSenderMailRequestException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoRecipientsSenderMailRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRecipientsSenderMailRequestException(String message) {
		super(message);
	}

	public NoRecipientsSenderMailRequestException(Throwable cause) {
		super(cause);
	}

}
