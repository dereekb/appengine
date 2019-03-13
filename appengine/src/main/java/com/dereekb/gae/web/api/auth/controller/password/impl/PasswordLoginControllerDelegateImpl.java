package com.dereekb.gae.web.api.auth.controller.password.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerifiedException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link PasswordLoginControllerDelegate} implementation.
 *
 * @author dereekb
 */
public class PasswordLoginControllerDelegateImpl
        implements PasswordLoginControllerDelegate {

	private boolean refreshAllowed = true;

	private PasswordLoginService loginService;
	private PasswordRecoveryService recoveryService;
	private LoginTokenService<LoginToken> tokenService;

	public PasswordLoginControllerDelegateImpl(PasswordLoginService loginService,
	        PasswordRecoveryService recoveryService,
	        LoginTokenService<LoginToken> tokenService) throws IllegalArgumentException {
		this.setLoginService(loginService);
		this.setRecoveryService(recoveryService);
		this.setTokenService(tokenService);
	}

	public boolean isRefreshAllowed() {
		return this.refreshAllowed;
	}

	public void setRefreshAllowed(boolean refreshAllowed) {
		this.refreshAllowed = refreshAllowed;
	}

	public PasswordLoginService getLoginService() {
		return this.loginService;
	}

	public void setLoginService(PasswordLoginService loginService) throws IllegalArgumentException {
		if (loginService == null) {
			throw new IllegalArgumentException();
		}

		this.loginService = loginService;
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

	public LoginTokenService<LoginToken> getTokenService() {
		return this.tokenService;
	}

	public void setTokenService(LoginTokenService<LoginToken> tokenService) throws IllegalArgumentException {
		if (tokenService == null) {
			throw new IllegalArgumentException();
		}

		this.tokenService = tokenService;
	}

	// MARK: PasswordLoginControllerDelegate
	@Override
	public LoginTokenPair createLogin(PasswordLoginPair pair) throws LoginExistsException {
		LoginPointer pointer = this.loginService.create(pair);
		return this.buildToken(pointer);
	}

	@Override
	public LoginTokenPair login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException {
		LoginPointer loginPointer = this.loginService.login(pair);

		String loginPointerId = loginPointer.getIdentifier();
		String loginToken = this.tokenService.encodeLoginToken(loginPointer, this.refreshAllowed);

		return new LoginTokenPair(loginPointerId, loginToken);
	}

	private LoginTokenPair buildToken(LoginPointer loginPointer) {
		String loginPointerId = loginPointer.getIdentifier();
		String loginToken = this.tokenService.encodeLoginToken(loginPointer, false);
		return new LoginTokenPair(loginPointerId, loginToken);
	}

	// MARK: PasswordRecoveryService
	@Override
	public void sendVerificationEmail(String email)
	        throws PasswordRecoveryVerifiedException,
	            UnregisteredEmailException,
	            PasswordRecoveryMailException {
		this.recoveryService.sendVerificationEmail(email);
	}

	@Override
	public void verifyUserEmail(String verificationToken)
	        throws PasswordRecoveryVerifiedException,
	            PasswordRecoveryVerificationException {
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

	@Override
	public String toString() {
		return "PasswordLoginControllerDelegateImpl [refreshAllowed=" + this.refreshAllowed + ", loginService="
		        + this.loginService + ", recoveryService=" + this.recoveryService + ", tokenService="
		        + this.tokenService + "]";
	}

}
