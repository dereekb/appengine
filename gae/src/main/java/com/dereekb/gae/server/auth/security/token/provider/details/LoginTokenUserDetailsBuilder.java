package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;


public interface LoginTokenUserDetailsBuilder {

	/**
	 * Builds a new {@link LoginTokenUserDetails} instance from the
	 * {@link LoginToken}.
	 *
	 * @param loginToken
	 *            {@link LoginToken}. Never {@code null}.
	 * @return {@link LoginTokenUserDetails}
	 */
	public LoginTokenUserDetails buildDetails(LoginToken loginToken);

}