package com.dereekb.gae.server.mail.exceptions;

/**
 * Throw when an email cannot be sent, or failed sending.
 * 
 * @author dereekb
 *
 */
public class EmailSendFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailSendFailureException() {}

	public EmailSendFailureException(String message) {
		super(message);
	}

}
