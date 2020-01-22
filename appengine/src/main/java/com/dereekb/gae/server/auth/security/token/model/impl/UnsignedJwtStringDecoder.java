package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.JwtStringDecoder;
import com.dereekb.gae.server.auth.security.token.model.JwtStringDencoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * {@link JwtStringDecoder} implementation that only decodes, and
 * reads all tokens without verifying the signature.
 *
 * @author dereekb
 *
 */
public class UnsignedJwtStringDecoder
        implements JwtStringDecoder, JwtStringDencoder {

	public static final UnsignedJwtStringDecoder SINGLETON = new UnsignedJwtStringDecoder();

	// private static final String EMPTY_SIGNING_KEY = "";
	private static final JwtParser DEFAULT_PARSER = Jwts.parser();

	// MARK: JwtStringDencoder
	@Override
	public String encodeClaims(Claims claims) {
		throw new UnsupportedOperationException("Only configured to decode insecure claims.");
	}

	@Override
	public Claims decodeTokenClaims(String token) {
		String[] splitToken = token.split("\\.");
		String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

		Jwt<?, ?> jwt = DEFAULT_PARSER.parse(unsignedToken);
		Claims claims = (Claims) jwt.getBody();
		return claims;
	}

	@Override
	public String toString() {
		return "UnsignedJwtStringDecoder []";
	}

}
