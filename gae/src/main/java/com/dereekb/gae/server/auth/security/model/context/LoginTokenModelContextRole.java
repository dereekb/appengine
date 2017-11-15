package com.dereekb.gae.server.auth.security.model.context;

import org.springframework.security.core.GrantedAuthority;

/**
 * {@link LoginTokenModelContext} role.
 * <p>
 * Is very similar to a {@link GrantedAuthority}, but doesn't grants roles in
 * the same way. As such, it implements the same but is different.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextRole {

	/**
	 * Returns the role.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getRole();

}
