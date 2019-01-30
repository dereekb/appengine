package com.dereekb.gae.server.mail.service;

/**
 * Email/Username combo.
 * 
 * @author dereekb
 *
 */
public interface MailUser {

	/**
	 * Returns the user's name.
	 * 
	 * @return {@link String}, or {@code null} if not defined.
	 */
	public String getName();

	/**
	 * Returns the email address.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEmailAddress();

}
