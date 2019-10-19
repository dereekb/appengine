package com.dereekb.gae.utilities.task.impl;

import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Noop {@link Task} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public class NoOpTask<T>
        implements Task<T> {

	@Override
	public void doTask(T input) throws FailedTaskException {
		// Do nothing.
	}

}
