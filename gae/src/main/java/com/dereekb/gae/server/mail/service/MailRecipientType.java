package com.dereekb.gae.server.mail.service;

/**
 * {@link MailRecipient} type.
 * 
 * @author dereekb
 *
 */
public enum MailRecipientType {

	/**
	 * Normal Recipient
	 */
	TO,

	/**
	 * Carbon Copy
	 */
	CC,

	/**
	 * Blind Carbon Copy
	 */
	BCC

}
