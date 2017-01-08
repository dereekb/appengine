package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Used for retrieving/building {@link KeyloginStatusService} instances for a
 * specific {@link Login}.
 * 
 * @author dereekb
 *
 */
public interface KeyLoginStatusServiceManager {

	/**
	 * Retrieves a {@link KeyLoginStatusService} for a {@link Login}.
	 * 
	 * @param login
	 *            {@link Login}. Never {@code null}.
	 * @return {@link KeyLoginStatusService}. Never {@code null}.
	 */
	public KeyLoginStatusService getService(Login login);

}
