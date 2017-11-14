package com.dereekb.gae.server.auth.security.model.context;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * {@link LoginTokenModelContext} role.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextRole
        extends IndexCoded, GrantedAuthority {

	/**
	 * Returns the role.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getRole();

}
