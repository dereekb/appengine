package com.dereekb.gae.server.auth.security.login.password.recover.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerKeyFormatter;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.impl.LoginPointerKeyFormatterImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryServiceTokenDelegate;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link PasswordRecoveryServiceTokenDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryServiceTokenDelegateImpl
        implements PasswordRecoveryServiceTokenDelegate {

	private static final String VERIFICATION_SUBJECT = "VERIFICATION";
	private static final String RECOVERY_SUBJECT = "RECOVERY";

	private LoginPointerKeyFormatter formatter = new LoginPointerKeyFormatterImpl(LoginPointerType.PASSWORD);

	private LoginPointerService pointerService;
	private LoginTokenService<LoginToken> loginTokenService;

	public PasswordRecoveryServiceTokenDelegateImpl(LoginPointerService pointerService,
	        LoginTokenService<LoginToken> loginTokenService) {
		super();
		this.setPointerService(pointerService);
		this.setLoginTokenService(loginTokenService);
	}

	public LoginPointerKeyFormatter getFormatter() {
		return this.formatter;
	}

	public void setFormatter(LoginPointerKeyFormatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("formatter cannot be null.");
		}

		this.formatter = formatter;
	}

	public LoginPointerService getPointerService() {
		return this.pointerService;
	}

	public void setPointerService(LoginPointerService pointerService) {
		if (pointerService == null) {
			throw new IllegalArgumentException("pointerService cannot be null.");
		}

		this.pointerService = pointerService;
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

	// MARK: PasswordRecoveryServiceTokenDelegate
	@Override
	public String generateVerificationToken(LoginPointer pointer) {
		LoginTokenImpl token = (LoginTokenImpl) this.loginTokenService.buildLoginToken(pointer, false);
		token.setSubject(VERIFICATION_SUBJECT);

		// TODO: Consider whether or not to lock to an app.

		return this.loginTokenService.encodeLoginToken(token);
	}

	@Override
	public void verifyLoginTokenEmail(String verificationToken) throws PasswordRecoveryVerificationExpiredException, PasswordRecoveryVerificationException {

		try {
			DecodedLoginToken<LoginToken> decoded = this.loginTokenService.decodeLoginToken(verificationToken);

			LoginToken token = decoded.getLoginToken();
			boolean isValid = (token.getSubject() == VERIFICATION_SUBJECT);

			if (!isValid) {
				throw new PasswordRecoveryVerificationException();
			}

			String loginPointer = token.getLoginPointerId();
			ModelKey loginPointerKey = new ModelKey(loginPointer);

			try {
				this.pointerService.changeVerified(loginPointerKey, true);
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
	public String generateRecoveryToken(LoginPointer pointer) {
		LoginTokenImpl token = (LoginTokenImpl) this.loginTokenService.buildLoginToken(pointer, false);
		token.setSubject(RECOVERY_SUBJECT);

		// TODO: Consider whether or not to lock to an app.

		return this.loginTokenService.encodeLoginToken(token);
	}

	@Override
	public LoginPointer loadLoginPointerForUser(String username) throws UnavailableModelException {
		ModelKey key = this.formatter.getKeyForUsername(username);
		LoginPointer pointer = this.pointerService.getLoginPointer(key);
		UnavailableModelException.assertNotNull(pointer, key);
		return pointer;
	}

	@Override
	public LoginPointer loadLoginPointerForEmail(String email) throws UnavailableModelException {
		LoginPointer pointer = this.pointerService.findWithEmail(LoginPointerType.PASSWORD, email);
		UnavailableModelException.assertNotNull(pointer, "Login with email '" + email + "' was unavailable.");
		return pointer;
	}

	@Override
	public String toString() {
		return "PasswordRecoveryServiceTokenDelegateImpl [formatter=" + this.formatter + ", pointerService="
		        + this.pointerService + ", loginTokenService=" + this.loginTokenService + "]";
	}

}
