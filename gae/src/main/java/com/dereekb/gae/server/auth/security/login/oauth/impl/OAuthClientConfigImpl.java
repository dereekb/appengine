package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;

/**
 * {@link OAuthClientConfig} implementation.
 * 
 * @author dereekb
 *
 */
public class OAuthClientConfigImpl
        implements OAuthClientConfig {

	private String clientId;
	private String clientSecret;

	public OAuthClientConfigImpl(String clientId, String clientSecret) throws IllegalArgumentException {
		this.setClientId(clientId);
		this.setClientSecret(clientSecret);
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) throws IllegalArgumentException {
		if (clientId == null) {
			throw new IllegalArgumentException("ClientId cannot be null.");
		}

		this.clientId = clientId;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) throws IllegalArgumentException {
		if (clientSecret == null) {
			throw new IllegalArgumentException("ClientSecret cannot be null.");
		}

		this.clientSecret = clientSecret;
	}

}
