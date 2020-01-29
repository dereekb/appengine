package com.dereekb.gae.server.auth.security.token.refresh.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;
import com.dereekb.gae.server.auth.security.token.model.impl.AbstractBasicLoginTokenImplEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link LoginTokenEncoderDecoder} for refresh tokens.
 *
 * @author dereekb
 *
 */
public class RefreshTokenEncoderDecoder extends AbstractBasicLoginTokenImplEncoderDecoder<LoginTokenImpl> {

	private static final Integer TYPE_OVERRIDE = LoginPointerType.REFRESH_TOKEN.getId();

	@Deprecated
	public RefreshTokenEncoderDecoder(String secret) {
		super(secret);
	}

	@Deprecated
	public RefreshTokenEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
		super(secret, algorithm);
	}

	public RefreshTokenEncoderDecoder(SignatureConfiguration signature) {
		super(signature);
	}

	// MARK: EncoderDecoder
	@Override
	public LoginTokenImpl makeToken() {
		return new LoginTokenImpl();
	}

	@Override
	public LoginTokenImpl makeToken(LoginToken token) {
		return new LoginTokenImpl(token);
	}

	// MARK: Encode
	@Override
	protected void appendClaimsComponents(LoginTokenImpl loginToken,
	                                      Claims claims) {
		// Export login key for external use. Is not decoded below though.
		claims.put(LoginTokenEncoderDecoderImpl.LOGIN_KEY, loginToken.getLoginId());
		claims.put(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_KEY, loginToken.getLoginPointerId());
		claims.put(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_TYPE_KEY, TYPE_OVERRIDE);
	}

	// MARK: Decode
	@Override
	protected void initFromClaims(LoginTokenImpl loginToken,
	                              Claims claims)
	        throws TokenUnauthorizedException {
		super.initFromClaims(loginToken, claims);

		// Only decode the login pointer and type here.
		Number login = claims.get(LoginTokenEncoderDecoderImpl.LOGIN_KEY, Number.class);
		String loginPointer = claims.get(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_KEY, String.class);

		if (login == null || loginPointer == null) {
			throw new TokenUnauthorizedException("Invalid refresh token.");
		}

		loginToken.setLogin(login.longValue());
		loginToken.setLoginPointer(loginPointer);

		Number typeNumber = claims.get(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_TYPE_KEY, Number.class);

		Integer type = null;

		if (typeNumber != null) {
			type = typeNumber.intValue();
		}

		if (type != null) {
			loginToken.setPointerType(LoginPointerType.valueOf(type));
		}
	}

}
