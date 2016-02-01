package com.dereekb.gae.model.extension.iterate;

import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Executor that wraps a
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public interface IterateTaskExecutor<T> {

	/**
	 * Executes the task given the input.
	 *
	 * @param input
	 *            {@link IterateTaskInput}. Never {@code null}.
	 * @throws IterationLimitReachedException
	 *             thrown if the task limit has been reached.
	 * @throws FailedTaskException
	 *             thrown if the task fails.
	 */
	public void executeTask(IterateTaskInput input) throws IterationLimitReachedException, FailedTaskException;

}
