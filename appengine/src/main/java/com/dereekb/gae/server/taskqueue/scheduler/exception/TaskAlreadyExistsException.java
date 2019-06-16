package com.dereekb.gae.server.taskqueue.scheduler.exception;


/**
 * {@link SubmitTaskException} thrown when a task already exists.
 *
 * @author dereekb
 *
 */
public class TaskAlreadyExistsException extends SubmitTaskException {

	private static final long serialVersionUID = 1L;

	public TaskAlreadyExistsException() {
		super();
	}

	public TaskAlreadyExistsException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskAlreadyExistsException(String message) {
		super(message);
	}

	public TaskAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
