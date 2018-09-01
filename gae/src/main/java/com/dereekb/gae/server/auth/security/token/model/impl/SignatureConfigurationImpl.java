package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link SignatureConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class SignatureConfigurationImpl
        implements SignatureConfiguration {

	private static final String DEFAULT_SECRET = "DEFAULT_SECRET";
	public static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureAlgorithm.HS256;

	private String secret;
	private SignatureAlgorithm algorithm;

	public SignatureConfigurationImpl(String secret) {
		this(secret, DEFAULT_ALGORITHM);
	}

	public SignatureConfigurationImpl(String secret, SignatureAlgorithm algorithm) {
		this.setSecret(secret);
		this.setAlgorithm(algorithm);
	}

	public static SignatureConfiguration defaultSignature() {
		return new SignatureConfigurationImpl(DEFAULT_SECRET);
	}

	@Override
	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		if (secret == null || secret.isEmpty()) {
			throw new IllegalArgumentException("Secret cannot be null or empty.");
		}

		this.secret = secret;
	}

	@Override
	public SignatureAlgorithm getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(SignatureAlgorithm algorithm) {
		if (algorithm == null) {
			throw new IllegalArgumentException("Algorithm cannot be null.");
		}

		this.algorithm = algorithm;
	}

	@Override
	public String toString() {
		return "SignatureConfigurationImpl [secret=" + this.secret + ", algorithm=" + this.algorithm + "]";
	}

}
