package com.dereekb.gae.server.auth.security.login.password.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.impl.LoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.auth.security.login.password.PasswordRestriction;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.AbstractPasswordRecoveryServiceImpl;
import com.dereekb.gae.server.mail.service.MailService;

/**
 * {@link PasswordLoginService} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordLoginServiceImpl extends LoginServiceImpl
        implements PasswordLoginService {

	public static final String PASSWORD_PREFIX = "P";

	private static final String DEFAULT_FORMAT = PASSWORD_PREFIX + "%s";

	private PasswordEncoder encoder;
	private PasswordRestriction restriction;
	private PasswordRecoveryService recoveryService;

	public PasswordLoginServiceImpl(PasswordEncoder encoder, LoginPointerService pointerService, MailService mailService)
	        throws IllegalArgumentException {
		super(DEFAULT_FORMAT, pointerService);
		this.setEncoder(encoder);
	}

	public PasswordEncoder getEncoder() {
		return this.encoder;
	}

	public void setEncoder(PasswordEncoder encoder) throws IllegalArgumentException {
		if (encoder == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.encoder = encoder;
	}

	public PasswordRestriction getRestriction() {
		return this.restriction;
	}

	public void setRestriction(PasswordRestriction restriction) {
		if (restriction == null) {
			throw new IllegalArgumentException("restriction cannot be null.");
		}

		this.restriction = restriction;
	}

	public PasswordRecoveryService getRecoveryService() {
		return this.recoveryService;
	}

	public void setRecoveryService(PasswordRecoveryService recoveryService) {
		if (recoveryService == null) {
			throw new IllegalArgumentException("recoveryService cannot be null.");
		}

		this.recoveryService = recoveryService;
	}

	// MARK: PasswordLoginService
	@Override
	public LoginPointer login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException {

		String username = pair.getUsername();
		String password = pair.getPassword();

		LoginPointer loginPointer = this.getLogin(username);

		String encodedPassword = loginPointer.getPassword();

		if (encodedPassword == null) {
			encodedPassword = "";
		}

		boolean match = this.encoder.matches(password, encodedPassword);

		if (!match) {
			throw new InvalidLoginCredentialsException(username);
		}

		return loginPointer;
	}

	@Override
	public LoginPointer create(PasswordLoginPair pair) throws LoginExistsException {
		String username = pair.getUsername();
		String password = pair.getPassword();

		LoginPointer loginPointer = new LoginPointer();

		String encodedPassword = this.encoder.encode(password);
		loginPointer.setPassword(encodedPassword);
		loginPointer.setLoginPointerType(LoginPointerType.PASSWORD);

		return this.createLogin(username, loginPointer);
	}

	// MARK: PasswordRecoveryService
	@Override
	public void sendVerificationEmail(String email) throws PasswordRecoveryMailException {
		this.recoveryService.sendVerificationEmail(email);
	}

	@Override
	public void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException {
		this.recoveryService.verifyUserEmail(verificationToken);
	}

	@Override
	public void recoverPassword(String username) throws UnknownUsernameException {
		this.recoveryService.recoverPassword(username);
	}

	@Override
	public void recoverUsername(String email) throws UnregisteredEmailException {
		this.recoveryService.recoverUsername(email);
	}

	private class PasswordRecoveryServiceImpl extends AbstractPasswordRecoveryServiceImpl {

		// MARK: PasswordRecoveryService
		@Override
		protected LoginPointer loadLoginPointerForUser(String username) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected LoginPointer loadLoginPointerForEmail(String email) {
			// TODO Auto-generated method stub
			return null;
		}

	}


	@Override
	public String toString() {
		return "PasswordLoginServiceImpl [encoder=" + this.encoder + "]";
	}

}
