package com.dereekb.gae.server.mail.exception;

/**
 * Throw when an email fails sending.
 * 
 * @author dereekb
 *
 */
public class MailSendFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	public MailSendFailureException() {
		super();
	}

	public MailSendFailureException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MailSendFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSendFailureException(String message) {
		super(message);
	}

	public MailSendFailureException(Throwable cause) {
		super(cause);
	}

}
