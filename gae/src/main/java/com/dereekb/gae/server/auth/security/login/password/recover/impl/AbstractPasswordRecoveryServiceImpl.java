package com.dereekb.gae.server.auth.security.login.password.recover.impl;

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

	// MARK: PasswordRecoveryServiceImpl
	@Override
	public void sendVerificationEmail(String username) throws NoPasswordRecoveryAddressException, PasswordRecoveryVerifiedException, UnregisteredEmailException, PasswordRecoveryMailException {
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
				MailServiceRequest verificationRequest = this.generateVerificationEmail(verificationToken);

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
		LoginPointer pointer = this.loadLoginPointerForUser(username);

		if (pointer == null) {
			throw new UnknownUsernameException(username);
		} else {
			this.sendRecoveryEmail(pointer);
		}
	}

	@Override
	public void recoverUsername(String email) throws UnregisteredEmailException {
		LoginPointer pointer = this.loadLoginPointerForEmail(email);

		if (pointer == null) {
			throw new UnregisteredEmailException(email);
		} else {
			this.sendRecoveryEmail(pointer);
		}
	}

	// MARK: Internal
	protected void sendRecoveryEmail(LoginPointer pointer) throws PasswordRecoveryMailException {
		MailServiceRequest verificationRequest = this.generateRecoveryEmail(pointer);

		try {
			this.mailService.sendMail(verificationRequest);
		} catch (InvalidMailRequestException | MailSendFailureException e) {
			throw new PasswordRecoveryMailException(e);
		}
	}

	protected MailServiceRequest generateVerificationEmail(String verificationToken) {
		// TODO Auto-generated method stub
		return null;
	}

	protected MailServiceRequest generateRecoveryEmail(LoginPointer pointer) {
		// TODO Auto-generated method stub
		return null;
	}

	protected abstract String generateVerificationToken(LoginPointer pointer);

	protected abstract LoginPointer loadLoginPointerForUser(String username);

	protected abstract LoginPointer loadLoginPointerForEmail(String email);

}
