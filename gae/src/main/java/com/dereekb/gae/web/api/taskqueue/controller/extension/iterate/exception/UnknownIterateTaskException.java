package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.exception;

import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.TaskQueueIterateControllerEntry;

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

	public UnknownIterateTaskException(String message) {
		super(message);
	}

	public UnknownIterateTaskException(Throwable cause) {
		super(cause);
	}

}
