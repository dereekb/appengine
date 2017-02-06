package com.dereekb.gae.server.auth.old.security.authentication;

/**
 * Used to create configured {@link LoginAuthentication} instances.
 *
 * @author dereekb
 */
public interface LoginAuthenticationFactory {

	/**
	 * Creates a new {@link LoginAuthentication} from a {@link LoginTuple}.
	 *
	 * @param loginTuple
	 * @return a newly configured {@link LoginAuthentication} for the tuple.
	 */
	public LoginAuthentication makeAuthentication(LoginTuple loginTuple);

}
