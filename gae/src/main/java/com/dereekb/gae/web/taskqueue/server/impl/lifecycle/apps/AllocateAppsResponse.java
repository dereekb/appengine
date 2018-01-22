package com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps;

import java.util.List;

import com.dereekb.gae.server.app.model.app.App;

/**
 * {@link AllocateAppsTask} response.
 *
 * @author dereekb
 *
 */
public interface AllocateAppsResponse {

	/**
	 * Returns all created apps.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<App> getApps();

}
