package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link LoginTokenService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenServiceImpl extends LoginTokenEncoderDecoderImpl
        implements LoginTokenService {

	private LoginTokenBuilder builder;

	public LoginTokenServiceImpl(String secret, LoginTokenBuilder builder) {
		super(secret);
		this.setBuilder(builder);
	}

	public LoginTokenServiceImpl(String secret, SignatureAlgorithm algorithm, LoginTokenBuilder builder) {
		super(secret, algorithm);
		this.setBuilder(builder);
	}

	public LoginTokenBuilder getBuilder() {
		return this.builder;
	}

	public void setBuilder(LoginTokenBuilder builder) throws IllegalArgumentException {
		if (builder == null) {
			throw new IllegalArgumentException();
		}

		this.builder = builder;
	}

	// MARK: LoginTokenService
	@Override
	public String encodeAnonymousLoginToken(String anonymousId) {
		LoginToken token = this.buildAnonymousLoginToken(anonymousId);
		return this.encodeLoginToken(token);
	}

	@Override
	public LoginToken buildAnonymousLoginToken(String anonymousId) {
		return this.builder.buildAnonymousLoginToken(anonymousId);
	}

	@Override
	public String encodeLoginToken(LoginPointer pointer,
	                               boolean refreshAllowed) {
		LoginToken token = this.buildLoginToken(pointer, refreshAllowed);
		return this.encodeLoginToken(token);
	}

	@Override
	public LoginToken buildLoginToken(LoginPointer pointer,
	                                  boolean refreshAllowed) {
		return this.builder.buildLoginToken(pointer, refreshAllowed);
	}

	@Override
	public String toString() {
		return "LoginTokenServiceImpl [builder=" + this.builder + "]";
	}

}
