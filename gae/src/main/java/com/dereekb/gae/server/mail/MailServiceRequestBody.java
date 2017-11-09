package com.dereekb.gae.server.mail;

/**
 * {@link MailServiceRequest} body.
 * 
 * @author dereekb
 *
 */
public interface MailServiceRequestBody {

	/**
	 * Returns the e-mail subject.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getSubject();

	/**
	 * Returns the body type.
	 * 
	 * @return {@link MailServiceRequestBodyType}. Never {@code null}.
	 */
	public MailServiceRequestBodyType getBodyType();

	/**
	 * Returns the body content.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getBodyContent();

}
