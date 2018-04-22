package com.dereekb.gae.server.mail.service;

import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;

/**
 * Service for sending an email.
 *
 * @author dereekb
 *
 */
public interface MailService {

	/**
	 * Sends an email.
	 *
	 * @param request
	 *            {@link MailServiceRequest}. Never {@code null}.
	 *
	 * @throws InvalidMailRequestException
	 *             thrown if the input request is invalid.
	 * @throws MailSendFailureException
	 *             thrown if an error occurs while sending the message.
	 */
	public void sendMail(MailServiceRequest request) throws InvalidMailRequestException, MailSendFailureException;

}
