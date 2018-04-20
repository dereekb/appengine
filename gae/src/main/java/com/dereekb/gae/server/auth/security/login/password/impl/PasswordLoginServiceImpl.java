package com.dereekb.gae.server.auth.security.login.password.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dereekb.gae.model.exception.UnavailableModelException;
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
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationExpiredException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.AbstractPasswordRecoveryServiceImpl;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
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

	public PasswordLoginServiceImpl(PasswordEncoder encoder,
	        LoginPointerService pointerService,
	        MailService mailService) throws IllegalArgumentException {
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

	private static final String VERIFICATION_SUBJECT = "VERIFICATION";

	private class PasswordRecoveryServiceImpl extends AbstractPasswordRecoveryServiceImpl {

		private LoginTokenService<LoginToken> loginTokenService;

		public PasswordRecoveryServiceImpl(LoginTokenService<LoginToken> loginTokenService) {
			this.setLoginTokenService(loginTokenService);
		}

		public LoginTokenService<LoginToken> getLoginTokenService() {
			return this.loginTokenService;
		}

		public void setLoginTokenService(LoginTokenService<LoginToken> loginTokenService) {
			if (loginTokenService == null) {
				throw new IllegalArgumentException("loginTokenService cannot be null.");
			}

			this.loginTokenService = loginTokenService;
		}

		// MARK: PasswordRecoveryService
		@Override
		protected String generateVerificationToken(LoginPointer pointer) {
			LoginTokenImpl token = (LoginTokenImpl) this.loginTokenService.buildLoginToken(pointer, false);
			token.setSubject(VERIFICATION_SUBJECT);

			// Consider whether or not to lock to an app.

			return this.loginTokenService.encodeLoginToken(token);
		}

		@Override
		public void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException {

			try {
				DecodedLoginToken<LoginToken> decoded = this.loginTokenService.decodeLoginToken(verificationToken);

				LoginToken token = decoded.getLoginToken();
				boolean isValid = (token.getSubject() == VERIFICATION_SUBJECT);

				if (!isValid) {
					throw new PasswordRecoveryVerificationException();
				}

				String loginPointer = token.getLoginPointerId();

				LoginPointerService pointerService = PasswordLoginServiceImpl.this.getPointerService();
				ModelKey loginPointerKey = new ModelKey(loginPointer);

				try {
					pointerService.changeVerified(loginPointerKey, true);
				} catch (UnavailableModelException e) {
					throw new PasswordRecoveryVerificationException();
				}
			} catch (TokenExpiredException e) {
				throw new PasswordRecoveryVerificationExpiredException();
			} catch (TokenUnauthorizedException e) {
				throw new PasswordRecoveryVerificationException();
			}
		}

		@Override
		protected LoginPointer loadLoginPointerForUser(String username) {
			return PasswordLoginServiceImpl.this.loadLogin(username);
		}

		@Override
		protected LoginPointer loadLoginPointerForEmail(String email) {
			LoginPointerService pointerService = PasswordLoginServiceImpl.this.getPointerService();
			return pointerService.findWithEmail(LoginPointerType.PASSWORD, email);
		}

	}

	@Override
	public String toString() {
		return "PasswordLoginServiceImpl [encoder=" + this.encoder + "]";
	}

}
