package com.dereekb.gae.web.taskqueue.model.extension.iterate.utility;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for building {@link TaskRequest} for {@link TaskQueueIterateController}.
 *
 * @author dereekb
 *
 */
public class TaskQueueIterateRequestBuilder {

	private static final Method ITERATE_METHOD = Method.PUT;

	private String taskName;
	private String modelType;
	private IterateType iterateType = IterateType.SEQUENCE;

	// MARK: Configure
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException("taskName cannot be null.");
		}

		this.taskName = taskName;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public IterateType getIterateType() {
		return this.iterateType;
	}

	public void setIterateType(IterateType iterateType) {
		if (iterateType == null) {
			throw new IllegalArgumentException("iterateType cannot be null.");
		}

		this.iterateType = iterateType;
	}

	// MARK: Build
	public TaskRequest buildTaskRequest() {
		String url = this.iterateType.pathForTask(this.modelType, this.taskName);

		TaskRequestImpl request = new TaskRequestImpl(url, ITERATE_METHOD);

		// TODO: Add additional components to the request!

		return request;
	}

}
