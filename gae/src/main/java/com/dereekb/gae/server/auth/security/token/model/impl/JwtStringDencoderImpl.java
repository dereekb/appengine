package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.JwtStringDencoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link JwtStringDencoder} implementation.
 *
 * @author dereekb
 *
 */
public class JwtStringDencoderImpl
        implements JwtStringDencoder {

	public static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureAlgorithm.HS256;

	private String secret;
	private SignatureAlgorithm algorithm;

	public JwtStringDencoderImpl(String secret) {
		this(secret, DEFAULT_ALGORITHM);
	}

	public JwtStringDencoderImpl(String secret, SignatureAlgorithm algorithm) {
		this.setSecret(secret);
		this.setAlgorithm(algorithm);
	}

	private void setSecret(String secret) throws IllegalArgumentException {
		if (secret == null || secret.isEmpty()) {
			throw new IllegalArgumentException("Secret cannot be null or empty.");
		}

		this.secret = secret;
	}

	private void setAlgorithm(SignatureAlgorithm algorithm) {
		if (algorithm == null) {
			throw new IllegalArgumentException("Algorithm cannot be null.");
		}

		this.algorithm = algorithm;
	}

	// MARK: JwtStringDencoder
	@Override
	public String encodeClaims(Claims claims) {
		JwtBuilder builder = Jwts.builder().signWith(this.algorithm, this.secret);
		return builder.setClaims(claims).compact();
	}

	@Override
	public Claims decodeTokenClaims(String token) {
		JwtParser parsers = Jwts.parser().setSigningKey(this.secret);
		return parsers.parseClaimsJws(token).getBody();
	}

	@Override
	public String toString() {
		return "JwtStringDencoderImpl [secret=" + this.secret.length() + ", algorithm=" + this.algorithm + "]";
	}

}
