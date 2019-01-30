package com.dereekb.gae.server.mail.service;

/**
 * Recipient for a message.
 * 
 * @author dereekb
 *
 */
public interface MailRecipient extends MailUser {

	/**
	 * Returns the type of recipient.
	 * 
	 * @return {@link MailRecipientType}. Never {@code null}.
	 */
	public MailRecipientType getRecipientType();

}
