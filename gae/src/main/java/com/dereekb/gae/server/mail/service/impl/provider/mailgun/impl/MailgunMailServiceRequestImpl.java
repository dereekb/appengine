package com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl;

import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.impl.provider.impl.AbstractMailServiceProviderRequest;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailServiceRequest;

/**
 * {@link MailgunMailServiceRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class MailgunMailServiceRequestImpl extends AbstractMailServiceProviderRequest
        implements MailgunMailServiceRequest {

	public MailgunMailServiceRequestImpl(MailServiceRequest mailServiceRequest) {
		super(mailServiceRequest);
	}

	@Override
	public String toString() {
		return "MailgunMailServiceRequestImpl []";
	}

}
