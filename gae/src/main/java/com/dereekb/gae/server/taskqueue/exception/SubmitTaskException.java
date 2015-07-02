package com.dereekb.gae.server.taskqueue.exception;

import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;

/**
 * Thrown by {@link TaskRequestSystem} when a task cannot be submitted.
 * 
 * @author dereekb
 *
 */
public class SubmitTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SubmitTaskException() {
		super();
	}

	public SubmitTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SubmitTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubmitTaskException(String message) {
		super(message);
	}

	public SubmitTaskException(Throwable cause) {
		super(cause);
	}

}
