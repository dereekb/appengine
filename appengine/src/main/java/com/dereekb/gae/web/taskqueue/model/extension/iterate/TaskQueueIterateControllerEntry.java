package com.dereekb.gae.web.taskqueue.model.extension.iterate;

import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.InvalidIterateTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.UnknownIterateTaskException;

/**
 * Delegate for {@link TaskQueueIterateController} for performing an iteration
 * task.
 *
 * @author dereekb
 */
public interface TaskQueueIterateControllerEntry
        extends TaskQueueSequenceControllerEntry {

	/**
	 * Performs an iterate task.
	 *
	 * @param input
	 *            {@link IterateTaskRequest} instance for this task. Never
	 *            {@code null}.
	 * @throws UnknownIterateTaskException
	 *             thrown if no task is available with the input
	 *             {@code taskName} value.
	 * @throws InvalidIterateTaskException
	 *             thrown if a task was available but not able to be
	 *             initialized.
	 */
	public void performIterateTask(IterateTaskRequest request)
	        throws UnknownIterateTaskException,
	            InvalidIterateTaskException;

}
