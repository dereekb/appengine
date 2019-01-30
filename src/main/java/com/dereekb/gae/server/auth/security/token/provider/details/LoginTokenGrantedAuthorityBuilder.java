package com.dereekb.gae.server.auth.security.token.provider.details;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Used for retrieving {@link GrantedAuthority} values from a
 * {@link LoginToken}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenGrantedAuthorityBuilder<T extends LoginToken> {

	/**
	 * Returns the collection of {@link GrantedAuthority} for the input
	 * {@link LoginToken}.
	 * 
	 * @param token
	 *            Token. Never {@code null}.
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(T token);

}
