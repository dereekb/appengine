package com.dereekb.gae.server.mail.service.exceptions;

import com.dereekb.gae.server.deprecated.mail.MailManager;

/**
 * Thrown when attempting to send a mail through a without any recipients.
 * 
 * @author dereekb
 * 
 * @see MailManager
 */
public class NoEmailSenderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
