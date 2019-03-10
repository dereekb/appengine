package com.dereekb.gae.test.server.mail.provider;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.server.mail.service.impl.MailRecipientImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestBodyImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestImpl;
import com.dereekb.gae.server.mail.service.impl.MailUserImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailService;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceConfigurationImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceImpl;

/**
 * {@link MailgunMailService} tests.
 *
 * @author dereekb
 *
 */
public class MailgunMailServiceTests {

	private static final String SANDBOX_DOMAIN = "sandboxac03c39a759d4cf1b3511eb72715b996.mailgun.org";
	private static final String AUTHORIZED_EMAIL = "dereekb@gmail.com";
	private static final String API_KEY = "key-d7e36a9ead3fdb61983587832bd75b42";


	public static final String SPLITTER = "\\.";

	@Test
	public void testSendingEmail() throws InvalidMailRequestException, MailSendFailureException {

		MailgunMailServiceConfigurationImpl configuration = new MailgunMailServiceConfigurationImpl(API_KEY,
		        SANDBOX_DOMAIN);
		configuration.setTestMode(true);

		MailUser defaultSender = new MailUserImpl(AUTHORIZED_EMAIL);
		MailgunMailServiceImpl service = new MailgunMailServiceImpl(defaultSender, configuration);
		service.setSendInAllEnvironments(true);

		MailRecipient recipient = new MailRecipientImpl(AUTHORIZED_EMAIL);

		String subject = "Test Email!";
		String bodyContent = "Test Email!";
		MailServiceRequestBodyImpl body = new MailServiceRequestBodyImpl(subject, bodyContent);

		MailServiceRequestImpl request = new MailServiceRequestImpl();

		request.setRecipient(recipient);
		request.setBody(body);

		service.sendMail(request);
	}

	@Test
	public void testNoRecipientThrowsError() throws InvalidMailRequestException, MailSendFailureException {

		MailgunMailServiceConfigurationImpl configuration = new MailgunMailServiceConfigurationImpl(API_KEY,
		        SANDBOX_DOMAIN);
		configuration.setTestMode(true);

		MailUser defaultSender = new MailUserImpl(AUTHORIZED_EMAIL);
		MailgunMailServiceImpl service = new MailgunMailServiceImpl(defaultSender, configuration);
		service.setSendInAllEnvironments(true);

		String subject = "Test Email!";
		String bodyContent = "Test Email!";
		MailServiceRequestBodyImpl body = new MailServiceRequestBodyImpl(subject, bodyContent);

		MailServiceRequestImpl request = new MailServiceRequestImpl();

		request.setBody(body);

		try {
			service.sendMail(request);
		} catch (InvalidMailRequestException e) {

		}
	}

}
