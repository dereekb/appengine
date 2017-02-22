package com.dereekb.gae.web.taskqueue.model.extension.iterate.exception;

/**
 * Exception thrown when a task cannot be generated or would be invalid.
 * 
 * @author dereekb
 *
 */
public class InvalidIterateTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidIterateTaskException() {
		super();
	}

	public InvalidIterateTaskException(String taskName) {
		super("Invalid task name '" + taskName + "'.");
	}

	public InvalidIterateTaskException(Throwable cause) {
		super(cause);
	}

}
