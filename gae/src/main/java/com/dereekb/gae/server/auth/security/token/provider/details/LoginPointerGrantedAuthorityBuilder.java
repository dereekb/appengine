package com.dereekb.gae.server.auth.security.token.provider.details;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;

/**
 * Used for retrieving {@link GrantedAuthority} values for a
 * {@link LoginPointerType}.
 * 
 * @author dereekb
 *
 */
public interface LoginPointerGrantedAuthorityBuilder {

	/**
	 * Returns the set of {@link GrantedAuthority} for the input.
	 * 
	 * @param type
	 *            {@link LoginPointerType}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<? extends GrantedAuthority> getGrantedAuthorities(LoginPointerType type);

}
