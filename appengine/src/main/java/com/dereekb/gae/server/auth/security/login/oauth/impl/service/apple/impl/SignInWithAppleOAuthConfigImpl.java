package com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple.impl;

import java.security.PrivateKey;

import com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple.SignInWithAppleOAuthConfig;
import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link SignInWithAppleOAuthConfig} implementation.
 *
 * @author dereekb
 *
 */
public class SignInWithAppleOAuthConfigImpl
        implements SignInWithAppleOAuthConfig {

	private String teamId;
	private String clientId;
	private String keyId;
	private PrivateKeyProvider privateKeyProvider;

	public SignInWithAppleOAuthConfigImpl(String teamId,
	        String clientId,
	        String keyId,
	        PrivateKeyProvider privateKeyProvider) {
		this.setTeamId(teamId);
		this.setClientId(clientId);
		this.setKeyId(keyId);
		this.setPrivateKeyProvider(privateKeyProvider);
	}

	@Override
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		if (teamId == null) {
			throw new IllegalArgumentException("teamId cannot be null.");
		}

		this.teamId = teamId;
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		if (clientId == null) {
			throw new IllegalArgumentException("clientId cannot be null.");
		}

		this.clientId = clientId;
	}

	@Override
	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		if (keyId == null) {
			throw new IllegalArgumentException("keyId cannot be null.");
		}

		this.keyId = keyId;
	}

	public PrivateKeyProvider getPrivateKeyProvider() {
		return this.privateKeyProvider;
	}

	public void setPrivateKeyProvider(PrivateKeyProvider privateKeyProvider) {
		if (privateKeyProvider == null) {
			throw new IllegalArgumentException("privateKeyProvider cannot be null.");
		}

		this.privateKeyProvider = privateKeyProvider;
	}

	@Override
	public PrivateKey getPrivateKey() throws RuntimeException {
		return this.privateKeyProvider.getPrivateKey();
	}

	@Override
	public String toString() {
		return "SignInWithAppleOAuthConfigImpl [teamId=" + this.teamId + ", clientId=" + this.clientId + ", keyId="
		        + this.keyId + ", privateKeyProvider=" + this.privateKeyProvider + "]";
	}

}
