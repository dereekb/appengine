package com.dereekb.gae.server.auth.security.token.model.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;

/**
 * {@link LoginTokenEncoder} and {@link LoginTokenDecoder}
 *
 * @author dereekb
 *
 */
public class LoginTokenEncoderDecoderImpl
        implements LoginTokenEncoder, LoginTokenDecoder {

	private String secret;
	private Long expiration;

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	// MARK: LoginTokenEncoder
	@Override
	public String encodeLoginToken(LoginToken loginToken) {
		// TODO Auto-generated method stub
		return null;
	}

	// MARK: LoginTokenDecoder
	@Override
	public LoginToken decodeLoginToken(String token) {
		JwtParser parsers = Jwts.parser().setSigningKey(this.secret);
		Claims claims = parsers.parseClaimsJws(token).getBody();

		// TODO Auto-generated method stub
		return null;
	}

}
