package com.dereekb.gae.server.auth.security.login.password.recover;

import com.dereekb.gae.server.mail.service.MailServiceRequest;

/**
 * Used to generate a {@link MailServiceRequest} for the input email and
 * verification
 *
 * @author dereekb
 *
 */
public interface PasswordRecoveryServiceEmailDelegate {

	public MailServiceRequest generateVerificationEmail(String email,
	                                                    String verificationToken);

	public MailServiceRequest generateRecoveryEmail(String email,
	                                                String recoveryToken);

}
