package com.dereekb.gae.web.taskqueue.model.extension.iterate;

import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.InvalidIterateTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.exception.UnknownIterateTaskException;

/**
 * Delegate for {@link TaskQueueIterateController} for performing a sequence
 * task.
 *
 * @author dereekb
 */
public interface TaskQueueSequenceControllerEntry {

	/**
	 * Performs a sequence task.
	 *
	 * @param input
	 *            {@link SequenceTaskRequest} instance for this task. Never
	 *            {@code null}.
	 * @throws UnknownIterateTaskException
	 *             thrown if no task is available with the input
	 *             {@code taskName} value.
	 * @throws InvalidIterateTaskException
	 *             thrown if a task was available but not able to be
	 *             initialized.
	 */
	public void performSequenceTask(SequenceTaskRequest request)
	        throws UnknownIterateTaskException,
	            InvalidIterateTaskException;

}
