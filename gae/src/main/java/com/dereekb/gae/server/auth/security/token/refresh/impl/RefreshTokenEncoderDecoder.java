package com.dereekb.gae.server.auth.security.token.refresh.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.AbstractLoginTokenEncoderDecoder;
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
public class RefreshTokenEncoderDecoder extends AbstractLoginTokenEncoderDecoder {

	private static final LoginPointerType TYPE_OVERRIDE = LoginPointerType.REFRESH_TOKEN;

	public RefreshTokenEncoderDecoder(String secret) {
		super(secret);
	}

	public RefreshTokenEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
		super(secret, algorithm);
	}

	// MARK: Encode
	@Override
	protected void appendClaimsComponents(LoginToken loginToken,
	                                      Claims claims) {
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
		String loginPointer = claims.get(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_KEY, String.class);

		if (loginPointer == null) {
			throw new TokenUnauthorizedException("Invalid refresh token.");
		}

		loginToken.setLoginPointer(loginPointer);

		Number typeNumber = claims.get(LoginTokenEncoderDecoderImpl.LOGIN_POINTER_TYPE_KEY, Number.class);

		Integer type = null;

		if (typeNumber != null) {
			type = typeNumber.intValue();
		}

		if (type != null) {
			loginToken.setPointerType(LoginPointerType.valueOf(type));
		}

		loginToken.setAnonymous(false);
		loginToken.setRoles(null);
	}

}
