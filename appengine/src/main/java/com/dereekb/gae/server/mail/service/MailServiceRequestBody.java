package com.dereekb.gae.server.mail.service;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

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
	 * Returns body (typically template) parameters.
	 *
	 * @return {@link Parameters}. Never {@code null}.
	 */
	public Parameters getParameters();

	/**
	 * Returns the body content.
	 * <p>
	 * If the content is a template, this is the template key.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getBodyContent();

}
