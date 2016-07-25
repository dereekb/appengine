package com.dereekb.gae.web.api.auth.controller.anonymous;

import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link AnonymousLoginController} delegate.
 * 
 * @author dereekb
 *
 */
public interface AnonymousLoginControllerDelegate {

	/**
	 * Creates a new {@link LoginTokenPair}.
	 *
	 * @param anonymousId
	 *            Optional identifier. May be {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	public LoginTokenPair anonymousLogin(String anonymousId);

}
