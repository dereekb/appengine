package com.dereekb.gae.web.taskqueue.server.task;

import java.util.Map;

/**
 * {@link TaskQueueTaskController} request.
 *
 * @author dereekb
 *
 */
public interface TaskQueueTaskControllerRequest {

	/**
	 * Returns the task name.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTaskName();

	/**
	 * Returns the parameters for this request.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public Map<String, String> getParameters();

}
