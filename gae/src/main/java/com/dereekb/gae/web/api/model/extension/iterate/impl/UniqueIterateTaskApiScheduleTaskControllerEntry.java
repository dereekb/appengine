package com.dereekb.gae.web.api.model.extension.iterate.impl;

import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

/**
 * {@link ConfiguredIterateTaskApiScheduleTaskControllerEntry} extension that
 * also sets a unique task request name.
 * <p>
 * This is useful for patches for models of a certain type, etc.
 *
 * @author dereekb
 *
 */
public class UniqueIterateTaskApiScheduleTaskControllerEntry extends ConfiguredIterateTaskApiScheduleTaskControllerEntry {

	private String uniqueTaskName;

	public UniqueIterateTaskApiScheduleTaskControllerEntry(String modelType, String taskName, String uniqueTaskName) {
		super(modelType, taskName);
		this.setUniqueTaskName(uniqueTaskName);
	}

	public String getUniqueTaskName() {
		return this.uniqueTaskName;
	}

	public void setUniqueTaskName(String uniqueTaskName) {
		if (uniqueTaskName == null) {
			throw new IllegalArgumentException("uniqueTaskName cannot be null.");
		}

		this.uniqueTaskName = uniqueTaskName;
	}

	// MARK: AbstractSingleTaskApiScheduleTaskControllerEntry
	@Override
	public TaskRequestImpl makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {
		TaskRequestImpl taskRequest = super.makeTaskRequest(request);

		taskRequest.setName(this.uniqueTaskName);

		return taskRequest;
	}

	@Override
	public String toString() {
		return "UniqueIterateTaskApiScheduleTaskControllerEntry [uniqueTaskName=" + this.uniqueTaskName + "]";
	}

}
