package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.googlecode.objectify.Key;

/**
 * Used for authenticating using a {@link LoginKey}.
 * 
 * @author dereekb
 *
 * @see {@link KeyLoginAuthenticationService} for usage.
 */
public interface KeyLoginInfo {

	/**
	 * Returns the {@link Key} for the {@link LoginKey}.
	 * 
	 * @return {@link Key}. Never {@code null}.
	 */
	public ModelKey getKey();

	/**
	 * Returns the verification string.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getVerification();

}
