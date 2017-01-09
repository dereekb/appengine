package com.dereekb.gae.server.auth.security.login.key.impl;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginInfo;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link KeyLoginAuthenticationService} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyLoginAuthenticationServiceImpl
        implements KeyLoginAuthenticationService {

	private Getter<LoginKey> loginKeyGetter;
	private Getter<LoginPointer> loginPointerGetter;

	public KeyLoginAuthenticationServiceImpl(Getter<LoginKey> loginKeyGetter, Getter<LoginPointer> loginPointerGetter) {
		this.setLoginKeyGetter(loginKeyGetter);
		this.setLoginPointerGetter(loginPointerGetter);
	}

	public Getter<LoginKey> getLoginKeyGetter() {
		return loginKeyGetter;
	}

	public void setLoginKeyGetter(Getter<LoginKey> loginKeyGetter) throws IllegalArgumentException {
		if (loginKeyGetter == null) {
			throw new IllegalArgumentException("LoginKeyGetter cannot be null.");
		}

		this.loginKeyGetter = loginKeyGetter;
	}

	public Getter<LoginPointer> getLoginPointerGetter() {
		return loginPointerGetter;
	}

	public void setLoginPointerGetter(Getter<LoginPointer> loginPointerGetter) throws IllegalArgumentException {
		if (loginPointerGetter == null) {
			throw new IllegalArgumentException("LoginKeyGetter cannot be null.");
		}

		this.loginPointerGetter = loginPointerGetter;
	}

	// MARK: KeyLoginAuthenticationService
	@Override
	public LoginPointer login(KeyLoginInfo keyLoginInfo)
	        throws KeyLoginUnavailableException,
	            KeyLoginRejectedException {

		ModelKey modelKey = keyLoginInfo.getKey();

		LoginKey loginKey = loginKeyGetter.get(modelKey);

		if (loginKey == null) {
			throw new KeyLoginUnavailableException();
		}

		String verification = keyLoginInfo.getVerification();
		String actualVerification = loginKey.getVerification();

		if (verification.equals(actualVerification) == false) {
			throw new KeyLoginRejectedException();
		}

		ModelKey pointerKey = loginKey.getPointerModelKey();
		return this.loginPointerGetter.get(pointerKey);
	}

	@Override
	public String toString() {
		return "KeyLoginAuthenticationServiceImpl [loginKeyGetter=" + loginKeyGetter + ", loginPointerGetter="
		        + loginPointerGetter + "]";
	}

}
