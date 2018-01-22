package com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps.impl;

import java.util.List;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps.AllocateAppsResponse;

/**
 * {@link AllocateAppsResponse} implementation.
 *
 * @author dereekb
 *
 */
public class AllocateAppsResponseImpl
        implements AllocateAppsResponse {

	private List<App> apps;

	public AllocateAppsResponseImpl(List<App> apps) {
		this.setApps(apps);
	}

	@Override
	public List<App> getApps() {
		return this.apps;
	}

	public void setApps(List<App> apps) {
		if (apps == null) {
			throw new IllegalArgumentException("apps cannot be null.");
		}

		this.apps = apps;
	}

	@Override
	public String toString() {
		return "AllocateAppsResponseImpl [apps=" + this.apps + "]";
	}

}
