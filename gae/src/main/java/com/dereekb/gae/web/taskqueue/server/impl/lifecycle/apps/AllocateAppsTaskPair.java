package com.dereekb.gae.web.taskqueue.server.impl.lifecycle.apps;

import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * {@link HandlerPair} for {@link AllocateAppsTask}.
 *
 * @author dereekb
 *
 */
public class AllocateAppsTaskPair extends HandlerPair<AllocateAppsRequest, AllocateAppsResponse> {

	public AllocateAppsTaskPair(AllocateAppsRequest key) {
		super(key);
	}

	public AllocateAppsTaskPair(AllocateAppsRequest key, AllocateAppsResponse object) {
		super(key, object);
	}

	public void setResult(AllocateAppsResponse result) {
		this.object = result;
	}

	@Override
	public String toString() {
		return "AllocateAppsTaskPair [key=" + this.key + ", object=" + this.object + "]";
	}

}
