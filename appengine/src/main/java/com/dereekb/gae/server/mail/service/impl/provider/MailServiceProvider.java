package com.dereekb.gae.server.mail.service.impl.provider;

import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;

/**
 * Abstract {@link MailService} for a specific service.
 * 
 * @author dereekb
 *
 * @param <I>
 *            input request type
 * @param <O>
 *            output response type
 */
public abstract interface MailServiceProvider<I extends MailServiceProviderRequest, O>
        extends MailService {

	/**
	 * 
	 * @param input
	 *            Input. Never {@code null}.
	 * @return Response. Never {@code null}.
	 * 
	 * @throws InvalidMailRequestException
	 *             thrown if the input request is invalid.
	 * @throws MailSendFailureException
	 *             thrown if an error occurs while sending the message.
	 */
	public O sendMail(I input) throws InvalidMailRequestException, MailSendFailureException;

}
