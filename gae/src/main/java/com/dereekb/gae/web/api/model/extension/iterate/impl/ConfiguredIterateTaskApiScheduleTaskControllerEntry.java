package com.dereekb.gae.web.api.model.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.model.extension.iterate.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * {@link ApiScheduleTaskControllerEntry} implementation that is preconfigured
 * to call upon the {@link TaskQueueIterateController}.
 * <p>
 * Tasks can additionally be configured by request parameters.
 * 
 * @author dereekb
 *
 */
public class ConfiguredIterateTaskApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	public static final boolean DEFAULT_ALLOW_PARAMETERS = true;

	private String modelType;
	private String taskName;

	private boolean allowParameters = DEFAULT_ALLOW_PARAMETERS;

	public ConfiguredIterateTaskApiScheduleTaskControllerEntry(String modelType, String taskName) {
		this(modelType, taskName, DEFAULT_ALLOW_PARAMETERS);
	}

	public ConfiguredIterateTaskApiScheduleTaskControllerEntry(String modelType,
	        String taskName,
	        boolean allowParameters) {
		super();
		this.setModelType(modelType);
		this.setTaskName(taskName);
		this.setAllowParameters(allowParameters);
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

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException("task cannot be null.");
		}

		this.taskName = taskName;
	}

	public boolean isAllowParameters() {
		return this.allowParameters;
	}

	public void setAllowParameters(boolean allowParameters) {
		this.allowParameters = allowParameters;
	}

	// MARK: AbstractSingleTaskApiScheduleTaskControllerEntry
	@Override
	public TaskRequestImpl makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {

		String path = this.getPathForRequest();
		TaskRequestImpl taskRequest = new TaskRequestImpl(path);

		if (this.allowParameters) {
			Map<String, String> parameters = request.getEncodedParameters();
			taskRequest.setParameters(parameters);
		}

		return taskRequest;
	}

	protected String getPathForRequest() {
		return TaskQueueIterateController.pathForIterateTask(this.modelType, this.taskName);
	}

	@Override
	public String toString() {
		return "ConfiguredIterateTaskApiScheduleTaskControllerEntry [modelType=" + this.modelType + ", taskName="
		        + this.taskName + ", allowParameters=" + this.allowParameters + "]";
	}

}
