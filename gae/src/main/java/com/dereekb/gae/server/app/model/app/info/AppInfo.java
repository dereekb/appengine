package com.dereekb.gae.server.app.model.app.info;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * App information.
 *
 * @author dereekb
 *
 */
public interface AppInfo
        extends UniqueModel {

	/**
	 * Returns app version info if available.
	 *
	 * @return {@link AppServiceVersionInfo}, or {@code null}.
	 */
	public AppServiceVersionInfo getAppServiceVersionInfo();

}
