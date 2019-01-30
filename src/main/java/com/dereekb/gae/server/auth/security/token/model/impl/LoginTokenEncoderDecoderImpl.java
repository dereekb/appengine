package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Default {@link LoginTokenEncoderDecoder} implementation for
 * {@link LoginTokenImpl}.
 *
 * @author dereekb
 *
 */
public final class LoginTokenEncoderDecoderImpl extends AbstractLoginTokenImplEncoderDecoder<LoginTokenImpl> {

	@Deprecated
	public LoginTokenEncoderDecoderImpl(String secret) {
		super(secret);
	}

	@Deprecated
	public LoginTokenEncoderDecoderImpl(String secret, SignatureAlgorithm algorithm) {
		super(secret, algorithm);
	}

	public LoginTokenEncoderDecoderImpl(SignatureConfiguration signature) {
		super(signature);
	}

	// MARK: AbstractLoginTokenEncoderDecoder
	@Override
	public LoginTokenImpl makeToken() {
		return new LoginTokenImpl();
	}

	@Override
	public LoginTokenImpl makeToken(LoginToken token) {
		return new LoginTokenImpl(token);
	}

	@Override
	public String toString() {
		return "LoginTokenEncoderDecoderImpl []";
	}

}
