package com.dereekb.gae.server.auth.security.token.filter.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterDelegate;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterVerifier;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.provider.preauth.impl.PreAuthLoginTokenAuthenticationImpl;

/**
 * {@link LoginTokenAuthenticationFilterDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilterDelegateImpl
        implements LoginTokenAuthenticationFilterDelegate {

	private static final LoginTokenAuthenticationFilterVerifier DEFAULT_VERIFIER = new LoginTokenAuthenticationFilterVerifierImpl();

	private LoginTokenDecoder decoder;
	private AuthenticationManager authenticationManager;
	private LoginTokenAuthenticationFilterVerifier verifier;

	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder decoder,
	        AuthenticationManager authenticationManager) throws IllegalArgumentException {
		this(decoder, authenticationManager, DEFAULT_VERIFIER);
	}

	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder decoder,
	        AuthenticationManager authenticationManager,
	        LoginTokenAuthenticationFilterVerifier verifier) {
		super();
		this.setDecoder(decoder);
		this.setAuthenticationManager(authenticationManager);
		this.setVerifier(verifier);
	}

	public LoginTokenDecoder getDecoder() {
		return this.decoder;
	}

	public void setDecoder(LoginTokenDecoder decoder) throws IllegalArgumentException {
		if (decoder == null) {
			throw new IllegalArgumentException("Decoder cannot be null.");
		}

		this.decoder = decoder;
	}

	public AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) throws IllegalArgumentException {
		if (authenticationManager == null) {
			throw new IllegalArgumentException("AuthenticationManager cannot be null.");
		}

		this.authenticationManager = authenticationManager;
	}

	public LoginTokenAuthenticationFilterVerifier getVerifier() {
		return this.verifier;
	}

	public void setVerifier(LoginTokenAuthenticationFilterVerifier verifier) {
		if (verifier == null) {
			throw new IllegalArgumentException("verifier cannot be null.");
		}

		this.verifier = verifier;
	}

	// MARK: LoginTokenAuthenticationFilterDelegate
	@Override
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details,
	                                     HttpServletRequest request)
	        throws TokenException {
		DecodedLoginToken decodedLoginToken = this.decoder.decodeLoginToken(token);

		this.verifier.assertValidDecodedLoginToken(decodedLoginToken, request);

		Authentication preAuth = this.buildPreAuth(decodedLoginToken, details);
		return this.authenticationManager.authenticate(preAuth);
	}

	protected Authentication buildPreAuth(DecodedLoginToken decodedLoginToken,
	                                      WebAuthenticationDetails details) {
		return new PreAuthLoginTokenAuthenticationImpl(decodedLoginToken, details);
	}

	@Override
	public String toString() {
		return "LoginTokenAuthenticationFilterDelegateImpl [decoder=" + this.decoder + ", authenticationManager="
		        + this.authenticationManager + "]";
	}

}
