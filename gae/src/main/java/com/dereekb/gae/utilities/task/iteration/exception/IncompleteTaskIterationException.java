package com.dereekb.gae.utilities.task.iteration.exception;

import com.dereekb.gae.utilities.task.iteration.IterationTask;

/**
 * Thrown when a {@link IterationTask} did not use all input values, due to an
 * exception or other reason.
 *
 * @author dereekb
 *
 */
public class IncompleteTaskIterationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IncompleteTaskIterationException() {
		super();
	}

	public IncompleteTaskIterationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncompleteTaskIterationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompleteTaskIterationException(String message) {
		super(message);
	}

	public IncompleteTaskIterationException(Throwable cause) {
		super(cause);
	}

}
