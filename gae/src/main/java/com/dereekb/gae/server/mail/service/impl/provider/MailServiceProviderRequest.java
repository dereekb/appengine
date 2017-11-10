package com.dereekb.gae.server.mail.service.impl.provider;

import com.dereekb.gae.server.mail.service.MailServiceRequest;

/**
 * Abstract interface for {@link MailServiceProvider} requests.
 * 
 * @author dereekb
 *
 */
public abstract interface MailServiceProviderRequest {

	/**
	 * @return {@link MailServiceRequests}. Never {@code null}.
	 */
	public MailServiceRequest getMailServiceRequest();

}
