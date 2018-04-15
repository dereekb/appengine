package com.dereekb.gae.server.auth.security.token.filter.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.filter.AuthenticationLoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterVerifier;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;

/**
 * {@link AuthenticationLoginTokenDecoder} implementation.
 *
 * @author dereekb
 *
 */
public class AuthenticationLoginTokenDecoderImpl<T extends LoginToken>
        implements AuthenticationLoginTokenDecoder<T> {

	private LoginTokenDecoder<T> decoder;
	private LoginTokenAuthenticationFilterVerifier<T> verifier;

	public AuthenticationLoginTokenDecoderImpl(LoginTokenDecoder<T> decoder,
	        LoginTokenAuthenticationFilterVerifier<T> verifier) {
		this.setDecoder(decoder);
		this.setVerifier(verifier);
	}

	public LoginTokenDecoder<T> getDecoder() {
		return this.decoder;
	}

	public void setDecoder(LoginTokenDecoder<T> decoder) throws IllegalArgumentException {
		if (decoder == null) {
			throw new IllegalArgumentException("Decoder cannot be null.");
		}

		this.decoder = decoder;
	}

	public LoginTokenAuthenticationFilterVerifier<T> getVerifier() {
		return this.verifier;
	}

	public void setVerifier(LoginTokenAuthenticationFilterVerifier<T> verifier) {
		if (verifier == null) {
			throw new IllegalArgumentException("verifier cannot be null.");
		}

		this.verifier = verifier;
	}

	// MARK: AuthenticationLoginTokenDecoder
	@Override
	public DecodedLoginToken<T> authenticateLoginToken(String token,
	                                                   HttpServletRequest request)
	        throws TokenExpiredException,
	            TokenUnauthorizedException,
	            TokenException {

		DecodedLoginToken<T> decodedLoginToken = this.decoder.decodeLoginToken(token);
		this.verifier.assertValidDecodedLoginToken(decodedLoginToken, request);
		return decodedLoginToken;
	}

	@Override
	public String toString() {
		return "AuthenticationLoginTokenDecoderImpl [decoder=" + this.decoder + ", verifier=" + this.verifier + "]";
	}

}
