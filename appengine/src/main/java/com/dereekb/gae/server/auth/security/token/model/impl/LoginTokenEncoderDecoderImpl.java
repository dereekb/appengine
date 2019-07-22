package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;

/**
 * Default {@link LoginTokenEncoderDecoder} implementation for
 * {@link LoginTokenImpl}.
 *
 * @author dereekb
 *
 */
public final class LoginTokenEncoderDecoderImpl extends AbstractLoginTokenImplEncoderDecoder<LoginTokenImpl> {

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
