package com.dereekb.gae.utilities.task;

import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * A basic task that takes in input.
 * <p>
 * All tasks should be designed to be thread-safe.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public interface Task<T> {

	/**
	 * Performs the task.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @throws FailedTaskException
	 *             If the task did not complete successfully.
	 */
	public void doTask(T input) throws FailedTaskException;

}
