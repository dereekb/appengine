package com.dereekb.gae.server.auth.security.login.password.recover.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.NoPasswordRecoveryAddressException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerifiedException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Abstract {@link PasswordRecoveryService} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractPasswordRecoveryServiceImpl
        implements PasswordRecoveryService {

	private MailService mailService;

	public AbstractPasswordRecoveryServiceImpl(MailService mailService) {
		this.setMailService(mailService);
	}

	public MailService getMailService() {
		return this.mailService;
	}

	public void setMailService(MailService mailService) {
		if (mailService == null) {
			throw new IllegalArgumentException("mailService cannot be null.");
		}

		this.mailService = mailService;
	}

	// MARK: PasswordRecoveryServiceImpl
	@Override
	public void sendVerificationEmail(String username)
	        throws NoPasswordRecoveryAddressException,
	            PasswordRecoveryVerifiedException,
	            UnregisteredEmailException,
	            PasswordRecoveryMailException {
		LoginPointer pointer = this.loadLoginPointerForUser(username);

		String email = pointer.getEmail();

		if (StringUtility.isEmptyString(email)) {
			throw new NoPasswordRecoveryAddressException();
		} else {
			Boolean verified = pointer.getVerified();

			if (verified == true) {
				throw new PasswordRecoveryVerifiedException(username);
			} else {
				String verificationToken = this.generateVerificationToken(pointer);
				MailServiceRequest verificationRequest = this.generateVerificationEmail(email, verificationToken);

				try {
					this.mailService.sendMail(verificationRequest);
				} catch (InvalidMailRequestException | MailSendFailureException e) {
					throw new PasswordRecoveryMailException(e);
				}
			}
		}
	}

	@Override
	public abstract void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException;

	@Override
	public void recoverPassword(String username) throws UnknownUsernameException {
		try {
			LoginPointer pointer = this.loadLoginPointerForUser(username);
			this.sendRecoveryEmail(pointer);
		} catch (UnavailableModelException e) {
			throw new UnknownUsernameException(username);
		}
	}

	@Override
	public void recoverUsername(String email) throws UnregisteredEmailException {
		try {
			LoginPointer pointer = this.loadLoginPointerForEmail(email);
			this.sendRecoveryEmail(pointer);
		} catch (UnavailableModelException e) {
			throw new UnregisteredEmailException(email);
		}
	}

	// MARK: Internal
	protected void sendRecoveryEmail(LoginPointer pointer)
	        throws NoPasswordRecoveryAddressException,
	            PasswordRecoveryMailException {
		String email = pointer.getEmail();

		if (email == null) {
			throw new NoPasswordRecoveryAddressException();
		}

		String token = this.generateRecoveryToken(pointer);
		MailServiceRequest verificationRequest = this.generateRecoveryEmail(email, token);

		try {
			this.mailService.sendMail(verificationRequest);
		} catch (InvalidMailRequestException | MailSendFailureException e) {
			throw new PasswordRecoveryMailException(e);
		}
	}

	protected abstract MailServiceRequest generateVerificationEmail(String email,
	                                                                String verificationToken);

	protected abstract MailServiceRequest generateRecoveryEmail(String email,
	                                                            String recoveryToken);

	protected abstract String generateVerificationToken(LoginPointer pointer);

	protected abstract String generateRecoveryToken(LoginPointer pointer);

	protected abstract LoginPointer loadLoginPointerForUser(String username) throws UnavailableModelException;

	protected abstract LoginPointer loadLoginPointerForEmail(String email) throws UnavailableModelException;

}
