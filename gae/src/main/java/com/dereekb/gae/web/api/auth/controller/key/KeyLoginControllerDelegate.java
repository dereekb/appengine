package com.dereekb.gae.web.api.auth.controller.key;

import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Delegate for {@link KeyLoginController}.
 * 
 * @author dereekb
 *
 */
public interface KeyLoginControllerDelegate {

	// MARK: Status
	/**
	 * Enables login keys for the user.
	 * 
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	public ApiResponse enableKeyLogin();

	/**
	 * Returns a response stating the current status of login keys.
	 * 
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	public ApiResponse getKeyLoginStatus();

	// MARK: Login
	/**
	 * Attempts to login using an API key and verification code.
	 * 
	 * @param key
	 *            {@link String} API key identifier. Never {@code null}.
	 * @param verification
	 *            {@link String} verification key. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws KeyLoginRejectedException
	 * @throws KeyLoginUnavailableException
	 */
	public LoginTokenPair login(String key,
	                            String verification)
	        throws KeyLoginRejectedException,
	            KeyLoginUnavailableException;

}
