package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;

/**
 * {@link AbstractTestLoginTokenContextImpl} extension that uses a
 * {@link LoginTokenService} and a {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractLoginPointerTestLoginTokenContextImpl extends AbstractTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	private static String ANONYMOUS_LOGIN_TOKEN_KEY = "LOGIN-TOKEN";

	private LoginTokenService<LoginToken> service;
	private LoginPointer pointer = null;

	public AbstractLoginPointerTestLoginTokenContextImpl(LoginTokenService<LoginToken> service) {
		this.service = service;
	}

	public LoginTokenService<LoginToken> getService() {
		return this.service;
	}

	public void setService(LoginTokenService<LoginToken> service) {
		this.service = service;
	}

	public LoginPointer getPointer() {
		return this.pointer;
	}

	public void setPointer(LoginPointer pointer) {
		this.pointer = pointer;
	}

	// MARK: TestLoginTokenContext
	@Override
	public String getToken() {
		String token = null;
		LoginToken loginToken = this.getLoginToken();

		if (loginToken != null) {
			token = this.service.encodeLoginToken(loginToken);
		}

		return token;
	}

	@Override
	public LoginToken getLoginToken() {
		LoginToken token = null;

		if (this.pointer != null) {
			token = this.service.buildLoginToken(this.pointer, this.isRefreshAllowed());
		} else {
			token = this.service.buildAnonymousLoginToken(ANONYMOUS_LOGIN_TOKEN_KEY);
		}

		return token;
	}

	@Override
	public String generateAnonymousToken() {
		return this.service.encodeAnonymousLoginToken(ANONYMOUS_LOGIN_TOKEN_KEY);
	}

	@Override
	public void generateAnonymousLogin() {
		this.clearLogin();
		this.setDefaultToAnonymous(true);
	}

	@Override
	public void clearLogin() {
		this.pointer = null;
		this.setDefaultToAnonymous(false);
	}

	@Override
	public void setLogin(LoginPointer pointer) {
		this.pointer = pointer;
	}

	/**
	 * {@link TestLoginTokenPair} implementation.
	 *
	 * @author dereekb
	 *
	 */
	protected class TestLoginTokenPairImpl extends AbstractTestLoginTokenPairImpl
	        implements TestLoginTokenPair {

		public TestLoginTokenPairImpl(Login login, LoginPointer loginPointer) {
			super(login, loginPointer);
		}

		@Override
		protected String makeNewToken() {
			return AbstractLoginPointerTestLoginTokenContextImpl.this.service.encodeLoginToken(this.getLoginPointer(),
			        this.getLogin(), AbstractLoginPointerTestLoginTokenContextImpl.this.isRefreshAllowed());
		}

		@Override
		public LoginToken buildLoginToken() {
			return AbstractLoginPointerTestLoginTokenContextImpl.this.service.buildLoginToken(this.getLoginPointer(),
			        this.getLogin(), AbstractLoginPointerTestLoginTokenContextImpl.this.isRefreshAllowed());
		}

	}

}
