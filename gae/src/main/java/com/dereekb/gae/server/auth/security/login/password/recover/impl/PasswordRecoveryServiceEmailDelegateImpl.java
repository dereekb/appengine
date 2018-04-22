package com.dereekb.gae.server.auth.security.login.password.recover.impl;

import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryServiceEmailDelegate;
import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.MailServiceRequestBody;
import com.dereekb.gae.server.mail.service.MailServiceRequestBodyType;
import com.dereekb.gae.server.mail.service.impl.MailRecipientImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestBodyImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestImpl;

/**
 * {@link PasswordRecoveryServiceEmailDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryServiceEmailDelegateImpl
        implements PasswordRecoveryServiceEmailDelegate {

	// TODO: Add app url

	// MARK: PasswordRecoveryServiceEmailDelegate
	@Override
	public MailServiceRequest generateVerificationEmail(String email,
	                                                    String verificationToken) {
		MailServiceRequestImpl request = new MailServiceRequestImpl();

		String verificationLink = "";	// TODO: Generate link using app info.

		String subject = "Verify Your Email";
		String bodyContent = "<div>" + "<p>Click <a id=\"token\" href=\"" + verificationLink
		        + "\">this link</a> to verify your e-mail address.</p>" + "</div>";

		MailServiceRequestBody body = new MailServiceRequestBodyImpl(subject, bodyContent,
		        MailServiceRequestBodyType.HTML);
		request.setBody(body);

		MailRecipient recipient = new MailRecipientImpl(email);
		request.setRecipient(recipient);

		return request;
	}

	@Override
	public MailServiceRequest generateRecoveryEmail(String email,
	                                                String recoveryToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
