package com.dereekb.gae.server.mail.service;

/**
 * {@link MailServiceRequestBody} type.
 *
 * @author dereekb
 *
 */
public enum MailServiceRequestBodyType {

	PLAIN_TEXT,

	HTML,

	/**
	 * Used by certain systems to specify a mail template should be used.
	 */
	TEMPLATE

}
