package com.dereekb.gae.server.auth.security.token.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Basic pre-auth token.
 *
 * @author dereekb
 *
 */
public interface BasicLoginTokenAuthentication
        extends Authentication {

	@Override
	public LoginToken getCredentials();

	@Override
	public WebAuthenticationDetails getDetails();

}
