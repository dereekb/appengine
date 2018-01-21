package com.dereekb.gae.server.auth.security.app;

import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Details about a unique registered secure application.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecurityDetails
        extends UniqueModel {

	/**
	 * Returns the app's name.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppName();

	/**
	 * Returns the app's secret.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppSecret();

	/**
	 * Returns the security level of the app.
	 *
	 * @return {@link AppLoginSecurityLevel}. Never {@code null}.
	 */
	public AppLoginSecurityLevel getAppLoginSecurityLevel();

}
