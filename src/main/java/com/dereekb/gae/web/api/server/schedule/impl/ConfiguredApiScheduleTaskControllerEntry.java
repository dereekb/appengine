package com.dereekb.gae.web.api.server.schedule.impl;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

/**
 * {@link ApiScheduleTaskControllerEntry} implementation that is pre-configured
 * with a {@link TaskRequest}.
 * 
 * @author dereekb
 *
 */
public class ConfiguredApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	private TaskRequest request;

	public ConfiguredApiScheduleTaskControllerEntry(TaskRequest request) {
		this.request = request;
	}

	public TaskRequest getRequest() {
		return this.request;
	}

	public void setRequest(TaskRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("request cannot be null.");
		}

		this.request = request;
	}

	// MARK: AbstractSingleTaskApiScheduleTaskControllerEntry
	@Override
	public TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {
		return this.request;
	}

	@Override
	public String toString() {
		return "ConfiguredApiScheduleTaskControllerEntry [request=" + this.request + "]";
	}

}
