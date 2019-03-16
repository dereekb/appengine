package com.dereekb.gae.server.auth.security.app;

import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.AppName;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Details about a unique registered secure application.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecurityDetails
        extends AppName, UniqueModel {

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
