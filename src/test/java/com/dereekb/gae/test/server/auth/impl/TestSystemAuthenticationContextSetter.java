package com.dereekb.gae.test.server.auth.impl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;

public class TestSystemAuthenticationContextSetter
        implements TestAuthenticationContext {

	private LoginTokenDecoder<LoginToken> decoder;

	private SystemLoginTokenFactory loginTokenFactory;

	private LoginTokenAuthenticationProvider<LoginToken> authenticationProvider;

	public TestSystemAuthenticationContextSetter(LoginTokenDecoder<LoginToken> decoder,
	        SystemLoginTokenFactory loginTokenFactory,
	        LoginTokenAuthenticationProvider<LoginToken> authenticationProvider) {
		this.setDecoder(decoder);
		this.setLoginTokenFactory(loginTokenFactory);
		this.setAuthenticationProvider(authenticationProvider);
	}

	public LoginTokenDecoder<LoginToken> getDecoder() {
		return this.decoder;
	}

	public void setDecoder(LoginTokenDecoder<LoginToken> decoder) {
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

	public LoginTokenAuthenticationProvider<LoginToken> getAuthenticationProvider() {
		return this.authenticationProvider;
	}

	public void setAuthenticationProvider(LoginTokenAuthenticationProvider<LoginToken> authenticationProvider) {
		if (authenticationProvider == null) {
			throw new IllegalArgumentException("authenticationProvider cannot be null.");
		}

		this.authenticationProvider = authenticationProvider;
	}

	// MARK: TestAuthenticationContext
	@Override
	public void resetContext() {
		String encodedToken = this.loginTokenFactory.makeSystemToken().getEncodedLoginToken();
		DecodedLoginToken<LoginToken> decodedToken = this.decoder.decodeLoginToken(encodedToken);
		LoginTokenAuthentication<LoginToken> authentication = this.authenticationProvider.authenticate(decodedToken, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
