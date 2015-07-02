package com.dereekb.gae.server.auth.security.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Used to retrieve a {@link GrantedAuthority} collection for a {@link Login}.
 *
 * @author dereekb
 */
public interface LoginAuthenticationAuthorityDelegate {

	public Collection<? extends GrantedAuthority> getAuthorities(Login login);

}
