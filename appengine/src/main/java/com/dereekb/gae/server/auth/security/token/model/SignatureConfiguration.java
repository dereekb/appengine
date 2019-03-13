package com.dereekb.gae.server.auth.security.token.model;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Contains a secret and algorithm used for configurations.
 *
 * @author dereekb
 *
 */
public interface SignatureConfiguration {

	/**
	 * Returns the secret.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getSecret();

	/**
	 * Returns the algorithm.
	 *
	 * @return {@link SignatureAlgorithm}. Never {@code null}.
	 */
	public SignatureAlgorithm getAlgorithm();

}
