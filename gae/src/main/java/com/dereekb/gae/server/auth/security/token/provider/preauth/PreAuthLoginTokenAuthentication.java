package com.dereekb.gae.server.auth.security.token.provider.preauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * Basic pre-auth token.
 *
 * @author dereekb
 *
 */
public interface PreAuthLoginTokenAuthentication
        extends Authentication {

	@Override
	public DecodedLoginToken getCredentials();

	@Override
	public WebAuthenticationDetails getDetails();

}
