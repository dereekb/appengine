package com.dereekb.gae.server.mail.service.impl.provider.mailgun;

import com.dereekb.gae.server.mail.service.MailService;

/**
 * {@link MailService} for Mailgun.
 * 
 * @author dereekb
 *
 */
public interface MailgunMailService
        extends MailService {

	/**
	 * Mailgun specific
	 * {@link #sendMail(com.dereekb.gae.server.mail.service.MailServiceRequest)}.
	 * 
	 * @param input
	 *            {@link MailgunMailServiceRequest}. Never {@code null}.
	 * @return {@link MailgunMailServiceResponse}.
	 */
	public MailgunMailServiceResponse sendMail(MailgunMailServiceRequest input);

}
