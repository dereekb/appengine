package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;

/**
 * Used for authenticating using an API key.
 * 
 * @author dereekb
 *
 */
public interface KeyLoginAuthenticationService {

	/**
	 * Retrieves a {@link LoginPointer} using a {@link KeyLoginInfo}.
	 *
	 * @param keyLoginInfo
	 *            {@link KeyLoginInfo}. Never {@code null}.
	 * @return {@link LoginPointer}. Never {@code null}.
	 * 
	 * @throws KeyLoginUnavailableException
	 *             if the info provided doesn't have a corresponding
	 *             {@link LoginKey}.
	 * @throws KeyLoginRejectedException
	 *             if the info provided is rejected.
	 */
	public LoginPointer login(KeyLoginInfo keyLoginInfo) throws KeyLoginUnavailableException, KeyLoginRejectedException;

}
