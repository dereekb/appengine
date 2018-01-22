package com.dereekb.gae.web.taskqueue.server;

/**
 * {@link TaskQueueTaskController} entry.
 *
 * @author dereekb
 *
 */
public interface TaskQueueTaskControllerEntry {

	/**
	 * Performs the task.
	 *
	 * @param request
	 *            {@link TaskQueueTaskControllerRequest} implementation.
	 * @throws RuntimeException
	 *             thrown if the task fails.
	 */
	public void performTask(TaskQueueTaskControllerRequest request) throws RuntimeException;

}
