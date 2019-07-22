package com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.impl;

import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.TaskRequestHost;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link TaskRequestHost} implementation.
 *
 * @author dereekb
 *
 */
public class TaskRequestHostImpl implements TaskRequestHost {

	private String hostTarget;

	public TaskRequestHostImpl(String hostTarget) {
		this.setHostTarget(hostTarget);
	}

	// MARK: TaskRequestHost
	@Override
	public String getHostTarget() {
		return this.hostTarget;
	}

	public void setHostTarget(String hostTarget) {
		if (StringUtility.isEmptyString(hostTarget)) {
			throw new IllegalArgumentException("hostTarget cannot be null or empty.");
		}

		this.hostTarget = hostTarget;
	}

	@Override
	public String toString() {
		return "TaskRequestHostImpl [hostTarget=" + this.hostTarget + "]";
	}

}
