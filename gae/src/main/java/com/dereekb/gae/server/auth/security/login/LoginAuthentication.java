package com.dereekb.gae.server.auth.security.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * {@link Authentication} implementation that is for {@link Login} instances.
 *
 * @author dereekb
 *
 */
public interface LoginAuthentication
        extends Authentication {

	@Override
	public LoginUserDetails getPrincipal();

	@Override
	public WebAuthenticationDetails getDetails();

}
