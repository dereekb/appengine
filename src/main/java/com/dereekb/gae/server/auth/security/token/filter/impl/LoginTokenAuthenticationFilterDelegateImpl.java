package com.dereekb.gae.server.auth.security.token.filter.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.filter.AuthenticationLoginTokenDecoder;
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

	private AuthenticationManager authenticationManager;
	private AuthenticationLoginTokenDecoder<T> authenticationDecoder;

	@Deprecated
	@SuppressWarnings("unchecked")
	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder<T> decoder,
	        AuthenticationManager authenticationManager) {
		this(decoder, authenticationManager, (LoginTokenAuthenticationFilterVerifier<T>) DEFAULT_VERIFIER);
	}

	@Deprecated
	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder<T> decoder,
	        AuthenticationManager authenticationManager,
	        LoginTokenAuthenticationFilterVerifier<T> verifier) {
		this(authenticationManager, new AuthenticationLoginTokenDecoderImpl<T>(decoder, verifier));
	}

	public LoginTokenAuthenticationFilterDelegateImpl(AuthenticationManager authenticationManager,
	        AuthenticationLoginTokenDecoder<T> authenticationDecoder) {
		super();
		this.setAuthenticationManager(authenticationManager);
		this.setAuthenticationDecoder(authenticationDecoder);
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

	public AuthenticationLoginTokenDecoder<T> getAuthenticationDecoder() {
		return this.authenticationDecoder;
	}

	public void setAuthenticationDecoder(AuthenticationLoginTokenDecoder<T> authenticationDecoder) {
		if (authenticationDecoder == null) {
			throw new IllegalArgumentException("authenticationDecoder cannot be null.");
		}

		this.authenticationDecoder = authenticationDecoder;
	}

	// MARK: LoginTokenAuthenticationFilterDelegate
	@Override
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details,
	                                     HttpServletRequest request)
	        throws TokenException {
		DecodedLoginToken<T> decodedLoginToken = this.authenticationDecoder.authenticateLoginToken(token, request);
		Authentication preAuth = this.buildPreAuth(decodedLoginToken, details);
		return this.authenticationManager.authenticate(preAuth);
	}

	protected Authentication buildPreAuth(DecodedLoginToken<T> decodedLoginToken,
	                                      WebAuthenticationDetails details) {
		return new PreAuthLoginTokenAuthenticationImpl<T>(decodedLoginToken, details);
	}

	@Override
	public String toString() {
		return "LoginTokenAuthenticationFilterDelegateImpl [authenticationManager=" + this.authenticationManager
		        + ", authenticationDecoder=" + this.authenticationDecoder + "]";
	}

}
