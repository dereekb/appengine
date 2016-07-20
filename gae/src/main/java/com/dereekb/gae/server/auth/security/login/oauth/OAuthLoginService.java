package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Used for logging in.
 * 
 * @author dereekb
 *
 */
public interface OAuthLoginService {

	public LoginPointer login(OAuthAuthorizationInfo authCode);

}
