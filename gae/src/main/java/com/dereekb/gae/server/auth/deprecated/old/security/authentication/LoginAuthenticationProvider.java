package com.dereekb.gae.server.auth.old.security.authentication;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication provider used to check the current state of the
 * {@link LoginAuthentication}.
 *
 * Uses a map of {@link LoginAuthenticationProviderDelegate} instances to check
 * current authentication states.
 *
 * @author dereekb
 */
@Deprecated
public final class LoginAuthenticationProvider
        implements AuthenticationProvider {

	private Map<String, LoginAuthenticationProviderDelegate> delegates;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginAuthentication loginAuth = (LoginAuthentication) authentication;

		String loginSystem = loginAuth.getLoginSystem();
		LoginAuthenticationProviderDelegate delegate = this.delegates.get(loginSystem);

		boolean authorized = false;

		if (delegate == null) {
			throw new RuntimeException("No delegate configured to respond to '" + loginSystem + "'.");
		} else {
			authorized = delegate.isStillAuthorized(loginAuth);
		}

		if (!authorized) {
			throw new InsufficientAuthenticationException("The login is no longer authorized.");
		}

		return loginAuth;
	}

	/**
	 * Supports only the {@link LoginAuthentication} type.
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return LoginAuthentication.class.isAssignableFrom(authentication);
	}

}
