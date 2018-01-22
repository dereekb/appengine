package com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps.impl;

import com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps.AllocateAppsRequest;

/**
 * {@link AllocateAppsRequest} implementation.
 *
 * @author dereekb
 *
 */
public class AllocateAppsRequestImpl
        implements AllocateAppsRequest {

	private Integer appCount = 20;

	public AllocateAppsRequestImpl(Integer appCount) {
		this.setAppCount(appCount);
	}

	// MARK: AllocateAppsRequest
	@Override
	public Integer getAppCount() {
		return this.appCount;
	}

	public void setAppCount(Integer appCount) {
		if (appCount == null) {
			throw new IllegalArgumentException("appCount cannot be null.");
		}

		this.appCount = appCount;
	}

	@Override
	public String toString() {
		return "AllocateAppsRequestImpl [appCount=" + this.appCount + "]";
	}

}
