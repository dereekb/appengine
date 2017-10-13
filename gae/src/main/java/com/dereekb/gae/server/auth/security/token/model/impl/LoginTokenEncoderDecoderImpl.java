package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Default {@link LoginTokenEncoderDecoder} implementation for
 * {@link LoginTokenImpl}.
 * 
 * @author dereekb
 *
 */
public final class LoginTokenEncoderDecoderImpl extends AbstractLoginTokenImplEncoderDecoder<LoginTokenImpl> {

	public LoginTokenEncoderDecoderImpl(String secret) {
		super(secret);
	}
	
	public LoginTokenEncoderDecoderImpl(String secret, SignatureAlgorithm algorithm) {
		super(secret, algorithm);
	}

	// MARK: AbstractLoginTokenEncoderDecoder
	@Override
	protected LoginTokenImpl newLoginToken() {
		return new LoginTokenImpl();
	}

	@Override
	public String toString() {
		return "LoginTokenEncoderDecoderImpl []";
	}

}
