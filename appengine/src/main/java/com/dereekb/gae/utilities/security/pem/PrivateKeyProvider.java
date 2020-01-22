package com.dereekb.gae.utilities.security.pem;

import java.security.PrivateKey;

/**
 * Provides a private key.
 *
 * @author dereekb
 *
 */
public interface PrivateKeyProvider {

	/**
	 * Returns the private key.
	 *
	 * @return {@link PrivateKey}. Never {@code null}.
	 */
	public PrivateKey getPrivateKey() throws RuntimeException;

}
