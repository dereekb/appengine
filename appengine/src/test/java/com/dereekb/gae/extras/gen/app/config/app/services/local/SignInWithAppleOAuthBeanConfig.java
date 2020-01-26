package com.dereekb.gae.extras.gen.app.config.app.services.local;

/**
 * Used by {@link LoginTokenAppSecurityBeansConfigurerImpl} to configure the
 * SignInWithApple configuration.
 *
 * @author dereekb
 *
 */
public class SignInWithAppleOAuthBeanConfig {

	public static final String DEFAULT_PRIVATE_KEY_ENV_PATH_VAR = "APPLE_PRIVATE_KEY_FILE_PATH";

	private String teamId;
	private String clientId;
	private String keyId;
	private String privateKeyPathEnv;

	/**
	 * Whether or not to add this to production only.
	 */
	private boolean prodOnly = true;

	public SignInWithAppleOAuthBeanConfig(String teamId, String clientId, String keyId) {
		this(teamId, clientId, keyId, DEFAULT_PRIVATE_KEY_ENV_PATH_VAR);
	}

	public SignInWithAppleOAuthBeanConfig(String teamId, String clientId, String keyId, String privateKeyEnvPath) {
		this.setTeamId(teamId);
		this.setClientId(clientId);
		this.setKeyId(keyId);
		this.setPrivateKeyPathEnv(privateKeyEnvPath);
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

	public String getPrivateKeyPathEnv() {
		return this.privateKeyPathEnv;
	}

	public void setPrivateKeyPathEnv(String privateKeyPathEnv) {
		if (privateKeyPathEnv == null) {
			throw new IllegalArgumentException("privateKeyPathEnv cannot be null.");
		}

		this.privateKeyPathEnv = privateKeyPathEnv;
	}

	public boolean isProdOnly() {
		return this.prodOnly;
	}

	public void setProdOnly(boolean prodOnly) {
		this.prodOnly = prodOnly;
	}

	@Override
	public String toString() {
		return "SignInWithAppleOAuthBeanConfig [teamId=" + this.teamId + ", clientId=" + this.clientId + ", keyId="
		        + this.keyId + ", privateKeyPathEnv=" + this.privateKeyPathEnv + "]";
	}

}
