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
public interface LoginTokenGrantedAuthorityBuilder {

	public Collection<? extends GrantedAuthority> getAuthorities(LoginToken token);

}
