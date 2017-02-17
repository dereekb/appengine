package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 * {@link OAuthClientConfig} implementation.
 * 
 * @author dereekb
 *
 */
public final class OAuthDevelopmentClientConfig
        implements OAuthClientConfig {

	private OAuthClientConfig productionConfig;
	private OAuthClientConfig developmentConfig;

	private final OAuthClientConfig config;

	public OAuthDevelopmentClientConfig(OAuthClientConfig productionConfig, OAuthClientConfig developmentConfig)
	        throws IllegalArgumentException {
		this.setProductionConfig(productionConfig);
		this.setDevelopmentConfig(developmentConfig);
		this.config = this.getActualConfig();
	}

	public OAuthClientConfig getProductionConfig() {
		return this.productionConfig;
	}

	public OAuthClientConfig getDevelopmentConfig() {
		return this.developmentConfig;
	}

	// MARK: OAuthClientConfig
	@Override
	public String getClientId() {
		return this.config.getClientId();
	}

	@Override
	public String getClientSecret() {
		return this.config.getClientSecret();
	}

	// MARK: Internal
	private OAuthClientConfig getActualConfig() {
		if (GoogleAppEngineUtility.isDevelopmentEnvironment()) {
			return this.developmentConfig;
		} else {
			return this.productionConfig;
		}
	}

	private void setProductionConfig(OAuthClientConfig productionConfig) throws IllegalArgumentException {
		if (productionConfig == null) {
			throw new IllegalArgumentException("productionConfig cannot be null.");
		}

		this.productionConfig = productionConfig;
	}

	private void setDevelopmentConfig(OAuthClientConfig developmentConfig) throws IllegalArgumentException {
		if (developmentConfig == null) {
			throw new IllegalArgumentException("developmentConfig cannot be null.");
		}

		this.developmentConfig = developmentConfig;
	}

	@Override
	public String toString() {
		return "OAuthDevelopmentClientConfig [productionConfig=" + this.productionConfig + ", developmentConfig="
		        + this.developmentConfig + "]";
	}

}
