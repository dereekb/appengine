package com.dereekb.gae.server.mail.exceptions;

import com.dereekb.gae.server.mail.MailManager;

/**
 * Thrown when attempting to send a mail through a without any recipients.
 * 
 * @author dereekb
 * @see {@link MailManager}
 */
public class NoEmailSenderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
