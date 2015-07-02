package com.dereekb.gae.server.mail;

import com.dereekb.gae.server.mail.exceptions.EmailSendFailureException;

/**
 * A source used by a {@link MailManager} for sending messages.
 * 
 * @author dereekb
 */
public interface MailSource {

	public void sendMail(MailRequest request) throws EmailSendFailureException;

}
