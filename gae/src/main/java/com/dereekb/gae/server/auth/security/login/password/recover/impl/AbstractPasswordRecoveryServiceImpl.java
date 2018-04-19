package com.dereekb.gae.server.auth.security.login.password.recover.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.server.mail.service.MailService;

/**
 * Abstract {@link PasswordRecoveryService} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractPasswordRecoveryServiceImpl
        implements PasswordRecoveryService {

	private MailService mailService;

	// MARK: PasswordRecoveryServiceImpl
	@Override
	public void sendVerificationEmail(String username) throws PasswordRecoveryMailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException {


	}

	@Override
	public void recoverPassword(String username) throws UnknownUsernameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void recoverUsername(String email) throws UnregisteredEmailException {
		// TODO Auto-generated method stub

	}

	// MARK: Internal


	protected abstract LoginPointer loadLoginPointerForUser(String username);

	protected abstract LoginPointer loadLoginPointerForEmail(String email);

}
