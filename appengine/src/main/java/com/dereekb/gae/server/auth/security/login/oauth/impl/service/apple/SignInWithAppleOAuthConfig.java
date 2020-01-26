package com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple;

import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link SignInWithAppleOAuthService} configuration.
 *
 * @author dereekb
 *
 */
public interface SignInWithAppleOAuthConfig
        extends PrivateKeyProvider {

	/**
	 * Returns the Apple team identifier.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTeamId();

	/**
	 * Returns the App's/Client identifier.
	 * <p>
	 * Example: com.your.bundle.id
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getClientId();

	/**
	 * Returns the Key's identifier.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getKeyId();

}
