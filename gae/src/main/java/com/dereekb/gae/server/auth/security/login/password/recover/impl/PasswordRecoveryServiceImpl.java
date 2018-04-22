package com.dereekb.gae.server.auth.security.login.password.recover.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryServiceEmailDelegate;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryServiceTokenDelegate;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.MailServiceRequest;

/**
 * {@link PasswordRecoveryService} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryServiceImpl extends AbstractPasswordRecoveryServiceImpl {

	private PasswordRecoveryServiceTokenDelegate tokenDelegate;
	private PasswordRecoveryServiceEmailDelegate emailDelegate;

	public PasswordRecoveryServiceImpl(MailService mailService,
	        PasswordRecoveryServiceTokenDelegate tokenDelegate,
	        PasswordRecoveryServiceEmailDelegate emailDelegate) {
		super(mailService);
		this.setTokenDelegate(tokenDelegate);
		this.setEmailDelegate(emailDelegate);
	}

	public PasswordRecoveryServiceTokenDelegate getTokenDelegate() {
		return this.tokenDelegate;
	}

	public void setTokenDelegate(PasswordRecoveryServiceTokenDelegate tokenDelegate) {
		if (tokenDelegate == null) {
			throw new IllegalArgumentException("tokenDelegate cannot be null.");
		}

		this.tokenDelegate = tokenDelegate;
	}

	public PasswordRecoveryServiceEmailDelegate getEmailDelegate() {
		return this.emailDelegate;
	}

	public void setEmailDelegate(PasswordRecoveryServiceEmailDelegate emailDelegate) {
		if (emailDelegate == null) {
			throw new IllegalArgumentException("emailDelegate cannot be null.");
		}

		this.emailDelegate = emailDelegate;
	}

	// MARK: PasswordRecoveryService
	@Override
	public void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException {
		this.tokenDelegate.verifyLoginTokenEmail(verificationToken);
	}

	@Override
	protected String generateVerificationToken(LoginPointer pointer) {
		return this.tokenDelegate.generateVerificationToken(pointer);
	}

	@Override
	protected String generateRecoveryToken(LoginPointer pointer) {
		return this.tokenDelegate.generateRecoveryToken(pointer);
	}

	@Override
	protected MailServiceRequest generateVerificationEmail(String email,
	                                                       String verificationToken) {
		return this.emailDelegate.generateVerificationEmail(email, verificationToken);
	}

	@Override
	protected MailServiceRequest generateRecoveryEmail(String email,
	                                                   String recoveryToken) {
		return this.emailDelegate.generateRecoveryEmail(email, recoveryToken);
	}

	@Override
	protected LoginPointer loadLoginPointerForUser(String username) throws UnavailableModelException {
		return this.tokenDelegate.loadLoginPointerForUser(username);
	}

	@Override
	protected LoginPointer loadLoginPointerForEmail(String email) throws UnavailableModelException {
		return this.tokenDelegate.loadLoginPointerForEmail(email);
	}

}
