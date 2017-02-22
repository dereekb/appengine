package com.dereekb.gae.test.server.auth.impl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;

public class TestSystemAuthenticationContextSetter
        implements TestAuthenticationContext {

	private LoginTokenDecoder decoder;

	private SystemLoginTokenFactory loginTokenFactory;

	private LoginTokenAuthenticationProvider authenticationProvider;

	public TestSystemAuthenticationContextSetter(LoginTokenDecoder decoder,
	        SystemLoginTokenFactory loginTokenFactory,
	        LoginTokenAuthenticationProvider authenticationProvider) {
		this.setDecoder(decoder);
		this.setLoginTokenFactory(loginTokenFactory);
		this.setAuthenticationProvider(authenticationProvider);
	}

	public LoginTokenDecoder getDecoder() {
		return this.decoder;
	}

	public void setDecoder(LoginTokenDecoder decoder) {
		if (decoder == null) {
			throw new IllegalArgumentException("decoder cannot be null.");
		}

		this.decoder = decoder;
	}

	public SystemLoginTokenFactory getLoginTokenFactory() {
		return this.loginTokenFactory;
	}

	public void setLoginTokenFactory(SystemLoginTokenFactory loginTokenFactory) {
		if (loginTokenFactory == null) {
			throw new IllegalArgumentException("loginTokenFactory cannot be null.");
		}

		this.loginTokenFactory = loginTokenFactory;
	}

	public LoginTokenAuthenticationProvider getAuthenticationProvider() {
		return this.authenticationProvider;
	}

	public void setAuthenticationProvider(LoginTokenAuthenticationProvider authenticationProvider) {
		if (authenticationProvider == null) {
			throw new IllegalArgumentException("authenticationProvider cannot be null.");
		}

		this.authenticationProvider = authenticationProvider;
	}

	// MARK: TestAuthenticationContext
	@Override
	public void resetContext() {
		String encodedToken = this.loginTokenFactory.makeTokenString();
		DecodedLoginToken decodedToken = this.decoder.decodeLoginToken(encodedToken);
		LoginTokenAuthentication authentication = this.authenticationProvider.authenticate(decodedToken, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
