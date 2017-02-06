package com.dereekb.gae.server.taskqueue.scheduler.exception;

/**
 * Thrown by {@link TaskRequestService} when a task cannot be submitted.
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
