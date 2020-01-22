package com.dereekb.gae.extras.gen.app.config.app.services.local;

/**
 * Used by {@link LoginTokenAppSecurityBeansConfigurerImpl} to configure the
 * SignInWithApple configuration.
 *
 * @author dereekb
 *
 */
public class SignInWithAppleOAuthBeanConfig {

	public static final String DEFAULT_PATH_FORMAT = "/WEB-INF/keys/%s.p8";
	public static final String DEFAULT_PATH = String.format(DEFAULT_PATH_FORMAT, "apple");

	private String teamId;
	private String clientId;
	private String keyId;
	private String privateKeyPath;

	public SignInWithAppleOAuthBeanConfig(String teamId, String clientId, String keyId, String privateKeyPath) {
		this.setTeamId(teamId);
		this.setClientId(clientId);
		this.setKeyId(keyId);
		this.setPrivateKeyPath(privateKeyPath);
	}

	public static SignInWithAppleOAuthBeanConfig makeSignInWithAppleOAuthBeanConfig(String teamId,
	                                                                                String clientId,
	                                                                                String keyId) {
		String path = String.format(DEFAULT_PATH_FORMAT, "AuthKey_" + keyId);
		return new SignInWithAppleOAuthBeanConfig(teamId, clientId, keyId, path);
	}

	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		if (teamId == null) {
			throw new IllegalArgumentException("teamId cannot be null.");
		}

		this.teamId = teamId;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		if (clientId == null) {
			throw new IllegalArgumentException("clientId cannot be null.");
		}

		this.clientId = clientId;
	}

	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		if (keyId == null) {
			throw new IllegalArgumentException("keyId cannot be null.");
		}

		this.keyId = keyId;
	}

	public String getPrivateKeyPath() {
		return this.privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		if (privateKeyPath == null) {
			throw new IllegalArgumentException("privateKeyPath cannot be null.");
		}

		this.privateKeyPath = privateKeyPath;
	}

	@Override
	public String toString() {
		return "SignInWithAppleOAuthBeanConfig [teamId=" + this.teamId + ", clientId=" + this.clientId + ", keyId="
		        + this.keyId + ", privateKeyPath=" + this.privateKeyPath + "]";
	}

}
