package com.dereekb.gae.server.auth.security.token.filter.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterDelegate;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterVerifier;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.provider.preauth.impl.PreAuthLoginTokenAuthenticationImpl;

/**
 * {@link LoginTokenAuthenticationFilterDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilterDelegateImpl<T extends LoginToken>
        implements LoginTokenAuthenticationFilterDelegate {

	@Deprecated
	protected static final LoginTokenAuthenticationFilterVerifier<LoginToken> DEFAULT_VERIFIER = new LoginTokenAuthenticationFilterVerifierImpl<LoginToken>();

	private LoginTokenDecoder<T> decoder;
	private AuthenticationManager authenticationManager;
	private LoginTokenAuthenticationFilterVerifier<T> verifier;

	@Deprecated
	@SuppressWarnings("unchecked")
	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder<T> decoder,
	                                      	        AuthenticationManager authenticationManager) {
	                                      		this(decoder, authenticationManager, (LoginTokenAuthenticationFilterVerifier<T>) DEFAULT_VERIFIER);
	                                      	}

	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder<T> decoder,
	        AuthenticationManager authenticationManager,
	        LoginTokenAuthenticationFilterVerifier<T> verifier) {
		super();
		this.setDecoder(decoder);
		this.setAuthenticationManager(authenticationManager);
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

	public AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) throws IllegalArgumentException {
		if (authenticationManager == null) {
			throw new IllegalArgumentException("AuthenticationManager cannot be null.");
		}

		this.authenticationManager = authenticationManager;
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

	// MARK: LoginTokenAuthenticationFilterDelegate
	@Override
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details,
	                                     HttpServletRequest request)
	        throws TokenException {
		DecodedLoginToken<T> decodedLoginToken = this.decoder.decodeLoginToken(token);

		this.verifier.assertValidDecodedLoginToken(decodedLoginToken, request);

		Authentication preAuth = this.buildPreAuth(decodedLoginToken, details);
		return this.authenticationManager.authenticate(preAuth);
	}

	protected Authentication buildPreAuth(DecodedLoginToken<T> decodedLoginToken,
	                                      WebAuthenticationDetails details) {
		return new PreAuthLoginTokenAuthenticationImpl<T>(decodedLoginToken, details);
	}

	@Override
	public String toString() {
		return "LoginTokenAuthenticationFilterDelegateImpl [decoder=" + this.decoder + ", authenticationManager="
		        + this.authenticationManager + "]";
	}

}
