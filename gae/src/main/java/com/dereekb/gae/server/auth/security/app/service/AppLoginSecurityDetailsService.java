package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Use to retrieve details about a specific app.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecurityDetailsService {

	/**
	 * Returns the login details.
	 *
	 * @param appId
	 *            {@link ModelKey}. Never {@code null}.
	 * @return {@link AppLoginSecurityDetails}. Never {@code null}.
	 */
	public AppLoginSecurityDetails getAppLoginDetails(ModelKey appId);

}
