package com.dereekb.gae.web.api.auth.controller.key;

import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.NoKeyLoginPointerException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Delegate for {@link KeyLoginController}.
 * 
 * @author dereekb
 *
 */
public interface KeyLoginControllerDelegate {

	//MARK: Status
	public ApiResponse enableKeyLogin();
	
	public ApiResponse getKeyLoginStatus();

	//MARK: Login
	/**
	 * 
	 * @param key {@link String} API key identifier. Never {@code null}.
	 * @param verification {@link String} verification key. Never {@code null}.
	 * @return
	 * @throws KeyLoginRejectedException
	 * @throws NoKeyLoginPointerException
	 */
	public LoginTokenPair login(String key,
	                            String verification) throws KeyLoginRejectedException, NoKeyLoginPointerException;

}
