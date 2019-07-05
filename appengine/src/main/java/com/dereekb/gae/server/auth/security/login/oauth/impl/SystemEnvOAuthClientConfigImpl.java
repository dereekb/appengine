package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link OAuthClientConfigImpl} extension that checks the env variable before
 * using the default client config.
 *
 * @author dereekb
 *
 */
public class SystemEnvOAuthClientConfigImpl
        implements OAuthClientConfig {

	public static final String CLIENT_ID_SUFFIX = "_ID";
	public static final String CLIENT_SECRET_SUFFIX = "_SECRET";

	private String clientIdEnvVariable;
	private String clientSecretEnvVariable;

	private OAuthClientConfig defaultClientConfig;

	public SystemEnvOAuthClientConfigImpl(String clientEnvVariablePrefix) {
		this(clientEnvVariablePrefix, null);
	}

	public SystemEnvOAuthClientConfigImpl(String clientEnvVariablePrefix, OAuthClientConfig defaultClientConfig) {
		super();
		this.setClientEnvVariablesWithPrefix(clientEnvVariablePrefix);
		this.setDefaultClientConfig(defaultClientConfig);
	}

	public SystemEnvOAuthClientConfigImpl(String clientIdEnvVariable,
	        String clientSecretEnvVariable,
	        OAuthClientConfig defaultClientConfig) {
		super();
		this.setClientIdEnvVariable(clientIdEnvVariable);
		this.setClientSecretEnvVariable(clientSecretEnvVariable);
		this.setDefaultClientConfig(defaultClientConfig);
	}

	public void setClientEnvVariablesWithPrefix(String prefix) {
		if (StringUtility.isEmptyString(prefix)) {
			throw new IllegalArgumentException("Prefix cannot be null or empty.");
		}

		this.setClientIdEnvVariable(prefix + CLIENT_ID_SUFFIX);
		this.setClientSecretEnvVariable(prefix + CLIENT_SECRET_SUFFIX);
	}

	public String getClientIdEnvVariable() {
		return this.clientIdEnvVariable;
	}

	public void setClientIdEnvVariable(String clientIdEnvVariable) {
		if (clientIdEnvVariable == null) {
			throw new IllegalArgumentException("clientIdEnvVariable cannot be null.");
		}

		this.clientIdEnvVariable = clientIdEnvVariable;
	}

	public String getClientSecretEnvVariable() {
		return this.clientSecretEnvVariable;
	}

	public void setClientSecretEnvVariable(String clientSecretEnvVariable) {
		if (clientSecretEnvVariable == null) {
			throw new IllegalArgumentException("clientSecretEnvVariable cannot be null.");
		}

		this.clientSecretEnvVariable = clientSecretEnvVariable;
	}

	public OAuthClientConfig getDefaultClientConfig() {
		return this.defaultClientConfig;
	}

	public void setDefaultClientConfig(OAuthClientConfig defaultClientConfig) {
		this.defaultClientConfig = defaultClientConfig;
	}

	// MARK: OAuthClientConfig
	@Override
	public String getClientId() {

		String clientId = System.getenv(this.clientIdEnvVariable);

		if (StringUtility.isEmptyString(clientId) && this.defaultClientConfig != null) {
			clientId = this.defaultClientConfig.getClientId();
		}

		if (StringUtility.isEmptyString(clientId)) {
			throw new UnsupportedOperationException("The client ID was not properly configured.");
		}

		return clientId;
	}

	@Override
	public String getClientSecret() {

		String clientSecret = System.getenv(this.clientSecretEnvVariable);

		if (StringUtility.isEmptyString(clientSecret) && this.defaultClientConfig != null) {
			clientSecret = this.defaultClientConfig.getClientSecret();
		}

		if (StringUtility.isEmptyString(clientSecret)) {
			throw new UnsupportedOperationException("The client secret was not properly configured.");
		}

		return clientSecret;
	}

	@Override
	public String toString() {
		return "SystemEnvOAuthClientConfigImpl [clientIdEnvVariable=" + this.clientIdEnvVariable
		        + ", clientSecretEnvVariable=" + this.clientSecretEnvVariable + ", defaultClientConfig="
		        + this.defaultClientConfig + "]";
	}

}
