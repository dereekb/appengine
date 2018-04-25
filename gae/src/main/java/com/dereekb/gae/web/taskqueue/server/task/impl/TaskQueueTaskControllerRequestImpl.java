package com.dereekb.gae.web.taskqueue.server.task.impl;

import java.util.Map;

import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskControllerRequest;

/**
 * {@link TaskQueueTaskControllerRequest} implementation.
 *
 * @author dereekb
 *
 */
public class TaskQueueTaskControllerRequestImpl
        implements TaskQueueTaskControllerRequest {

	private String taskName;
	private Map<String, String> parameters;

	private String type;

	public TaskQueueTaskControllerRequestImpl(String taskName, Map<String, String> parameters) {
		this.setTaskName(taskName);
		this.setParameters(parameters);
	}

	public TaskQueueTaskControllerRequestImpl(String taskName, Map<String, String> parameters, String type) {
		this.setTaskName(taskName);
		this.setParameters(parameters);
		this.setType(type);
	}

	@Override
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException("taskName cannot be null.");
		}

		this.taskName = taskName;
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException("parameters cannot be null.");
		}

		this.parameters = parameters;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TaskQueueTaskControllerRequestImpl [taskName=" + this.taskName + ", parameters=" + this.parameters
		        + ", type=" + this.type + "]";
	}

}
