package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate;

import com.dereekb.gae.model.extension.taskqueue.iterate.IterateTaskInput;
import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.exception.UnknownIterateTaskException;

/**
 * Delegate for {@link TaskQueueIterateController} for performing an iteration
 * task.
 *
 * @author dereekb
 */
public interface TaskQueueIterateControllerEntry {

	/**
	 * Performs the specified task.
	 *
	 * @param taskName
	 *            Name of the task to perform. Never {@code null}.
	 * @param input
	 *            {@link IterateTaskInput} instance for this task. Never
	 *            {@code null}.
	 * @throws UnknownIterateTaskException
	 *             thrown if no task is available with the input
	 *             {@code taskName} value.
	 */
	public void performTask(String taskName,
	                        IterateTaskInput input) throws UnknownIterateTaskException;

}
