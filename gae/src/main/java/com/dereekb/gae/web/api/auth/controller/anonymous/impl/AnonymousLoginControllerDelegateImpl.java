package com.dereekb.gae.web.api.auth.controller.anonymous.impl;

import java.util.UUID;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.web.api.auth.controller.anonymous.AnonymousLoginControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link AnonymousLoginControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class AnonymousLoginControllerDelegateImpl
        implements AnonymousLoginControllerDelegate {

	private LoginTokenService<LoginToken> tokenService;
	private Factory<String> idFactory = new AnonymousUuidGenerator();

	public AnonymousLoginControllerDelegateImpl(LoginTokenService<LoginToken> tokenService) {
		this.setTokenService(tokenService);
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

	public Factory<String> getIdFactory() {
		return this.idFactory;
	}

	public void setIdFactory(Factory<String> idFactory) {
		this.idFactory = idFactory;
	}

	// MARK: AnonymousLoginControllerDelegate
	@Override
	public LoginTokenPair anonymousLogin(String anonymousId) {

		if (anonymousId == null || anonymousId.isEmpty()) {
			anonymousId = this.idFactory.make();
		}

		String token = this.tokenService.encodeAnonymousLoginToken(anonymousId);
		return new LoginTokenPair(token);
	}

	private class AnonymousUuidGenerator implements Factory<String> {

		@Override
		public String make() throws FactoryMakeFailureException {
			UUID uuid = UUID.randomUUID();
			return uuid.toString();
		}

	}

	@Override
	public String toString() {
		return "AnonymousLoginControllerDelegateImpl [tokenService=" + this.tokenService + "]";
	}

}
