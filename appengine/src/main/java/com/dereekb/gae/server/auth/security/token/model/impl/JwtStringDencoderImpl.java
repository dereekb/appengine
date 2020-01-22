package com.dereekb.gae.server.auth.security.token.model.impl;

import java.security.Key;

import com.dereekb.gae.server.auth.security.token.model.JwtStringDencoder;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * {@link JwtStringDencoder} implementation.
 *
 * @author dereekb
 *
 */
public class JwtStringDencoderImpl
        implements JwtStringDencoder {

	private SignatureConfiguration signature;

	public JwtStringDencoderImpl(String secret, SignatureAlgorithm algorithm) {
		this(new SignatureConfigurationImpl(secret, algorithm));
	}

	public JwtStringDencoderImpl(SignatureConfiguration signature) {
		this.setSignature(signature);
	}

	public SignatureConfiguration getSignature() {
		return this.signature;
	}

	public void setSignature(SignatureConfiguration signature) {
		if (signature == null) {
			throw new IllegalArgumentException("signature cannot be null.");
		}

		this.signature = signature;
	}

	// MARK: JwtStringDencoder
	@Override
	public String encodeClaims(Claims claims) {
		String base64hmacKey = this.signature.getSecret();
		byte[] bytes = Decoders.BASE64.decode(base64hmacKey);
		Key key = Keys.hmacShaKeyFor(bytes);
		JwtBuilder builder = Jwts.builder().signWith(key, this.signature.getAlgorithm());
		return builder.setClaims(claims).compact();
	}

	@Override
	public Claims decodeTokenClaims(String token) {
		JwtParser parsers = Jwts.parser().setSigningKey(this.signature.getSecret());
		return parsers.parseClaimsJws(token).getBody();
	}

	@Override
	public String toString() {
		return "JwtStringDencoderImpl [signature=" + this.signature + "]";
	}

}
