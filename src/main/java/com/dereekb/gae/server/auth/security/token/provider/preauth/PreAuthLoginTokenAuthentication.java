package com.dereekb.gae.server.auth.security.token.provider.preauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Basic pre-auth token.
 *
 * @author dereekb
 *
 */
public interface PreAuthLoginTokenAuthentication<T extends LoginToken>
        extends Authentication {

	@Override
	public DecodedLoginToken<T> getCredentials();

	@Override
	public WebAuthenticationDetails getDetails();

}
