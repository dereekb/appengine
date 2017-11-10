package com.dereekb.gae.server.mail.service.impl.provider.impl;

import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.impl.provider.MailServiceProviderRequest;

/**
 * {@link MailServiceProviderRequest}.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractMailServiceProviderRequest
        implements MailServiceProviderRequest {

	private MailServiceRequest mailServiceRequest;

	public AbstractMailServiceProviderRequest(MailServiceRequest mailServiceRequest) {
		this.setMailServiceRequest(mailServiceRequest);
	}

	// MARK: MailServiceProviderRequest
	@Override
	public MailServiceRequest getMailServiceRequest() {
		return this.mailServiceRequest;
	}

	public void setMailServiceRequest(MailServiceRequest mailServiceRequest) {
		if (mailServiceRequest == null) {
			throw new IllegalArgumentException("mailServiceRequest cannot be null.");
		}

		this.mailServiceRequest = mailServiceRequest;
	}

}
