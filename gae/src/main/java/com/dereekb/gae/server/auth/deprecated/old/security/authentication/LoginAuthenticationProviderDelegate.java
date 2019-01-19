package com.dereekb.gae.server.auth.old.security.authentication;


@Deprecated
public interface LoginAuthenticationProviderDelegate {

	/**
	 * Checks whether or not the current authentication is still authorized.
	 *
	 * @param loginAuth
	 * @return true if the login auth is still authorized.
	 */
	public boolean isStillAuthorized(LoginAuthentication loginAuth);

}
