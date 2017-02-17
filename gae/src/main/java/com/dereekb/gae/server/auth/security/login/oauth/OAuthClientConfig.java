package com.dereekb.gae.server.auth.security.login.oauth;

/**
 * Client ID/Secret pair used by OAuth clients.
 * 
 * @author dereekb
 *
 */
public interface OAuthClientConfig {

	public String getClientId();

	public String getClientSecret();

}
