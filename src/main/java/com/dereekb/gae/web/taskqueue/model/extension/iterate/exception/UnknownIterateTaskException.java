package com.dereekb.gae.web.taskqueue.model.extension.iterate.exception;

import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateControllerEntry;

/**
 * Thrown by {@link TaskQueueIterateControllerEntry} when an unknown task is
 * requested to be performed.
 *
 * @author dereekb
 *
 */
public class UnknownIterateTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownIterateTaskException() {
		super();
	}

	public UnknownIterateTaskException(String taskName) {
		super("Unknown task name '" + taskName + "'.");
	}

	public UnknownIterateTaskException(Throwable cause) {
		super(cause);
	}

}
