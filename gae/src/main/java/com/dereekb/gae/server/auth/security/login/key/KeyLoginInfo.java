package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.googlecode.objectify.Key;

/**
 * Used for authenticating using a {@link LoginKey}.
 * 
 * @author dereekb
 *
 * @see {@link KeyLoginAuthneticationService} for usage.
 */
public interface KeyLoginInfo {

	public Key<LoginKey> getKey();
	
	public String getValidation();
	
}
