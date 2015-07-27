package com.dereekb.gae.utilities.task.iteration.exception;

import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.task.iteration.IterationTask;

/**
 * Thrown when a {@link IterationTask} did not use all input values, due to an
 * exception or other reason.
 *
 * @author dereekb
 *
 */
public class IncompleteTaskIterationException extends FailedTaskException {

	private static final long serialVersionUID = 1L;

	public IncompleteTaskIterationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompleteTaskIterationException(String message) {
		super(message);
	}

	public IncompleteTaskIterationException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "IncompleteTaskIterationException []";
	}

}
