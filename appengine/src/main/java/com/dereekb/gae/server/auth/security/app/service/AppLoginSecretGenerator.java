package com.dereekb.gae.server.auth.security.app.service;

/**
 * Generates a new secret.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecretGenerator {

	/**
	 * Generates a new secret.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String generateSecret();

}
