package com.dereekb.gae.utilities.task.exception;

/**
 * Thrown when a {@link Task} fails.
 *
 * @author dereekb
 *
 */
public class FailedTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedTaskException(String message) {
		super(message);
	}

	public FailedTaskException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "FailedTaskException []";
	}

}
