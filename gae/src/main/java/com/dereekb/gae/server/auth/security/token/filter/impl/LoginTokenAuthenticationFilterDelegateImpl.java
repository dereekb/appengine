package com.dereekb.gae.server.auth.security.token.filter.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterDelegate;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.preauth.impl.PreAuthLoginTokenAuthenticationImpl;

/**
 * {@link LoginTokenAuthenticationFilterDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilterDelegateImpl
        implements LoginTokenAuthenticationFilterDelegate {

	private LoginTokenDecoder decoder;
	private AuthenticationManager authenticationManager;

	public LoginTokenAuthenticationFilterDelegateImpl(LoginTokenDecoder decoder,
	        AuthenticationManager authenticationManager) throws IllegalArgumentException {
		this.setDecoder(decoder);
		this.setAuthenticationManager(authenticationManager);
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

	// MARK: LoginTokenAuthenticationFilterDelegate
	@Override
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details) {
		DecodedLoginToken decodedLoginToken = this.decoder.decodeLoginToken(token);
		PreAuthLoginTokenAuthentication preAuth = new PreAuthLoginTokenAuthenticationImpl(decodedLoginToken, details);
		return this.authenticationManager.authenticate(preAuth);
	}

	@Override
	public String toString() {
		return "LoginTokenAuthenticationFilterDelegateImpl [decoder=" + this.decoder + ", authenticationManager="
		        + this.authenticationManager + "]";
	}

}
