package com.dereekb.gae.server.auth.security.token.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Used for retrieving an {@link LoginTokenAuthentication} using a token.
 *
 * @author dereekb
 *
 */
public interface LoginTokenAuthenticationProvider
        extends AuthenticationProvider {

	/**
	 * Attempts to authenticate using the input token against the details.
	 *
	 * @param loginToken
	 *            {@link LoginToken}. Never {@code null}.
	 * @param details
	 *            {@link WebAuthenticationDetails}. Never {@code null}.
	 *
	 * @return {@link LoginTokenAuthentication}. Never {@code null}.
	 */
	public LoginTokenAuthentication authenticate(LoginToken loginToken,
	                                             WebAuthenticationDetails details);

}
