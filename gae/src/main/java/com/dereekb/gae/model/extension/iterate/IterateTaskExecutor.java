package com.dereekb.gae.model.extension.iterate;

import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;

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
	 */
	public void executeTask(IterateTaskInput input) throws IterationLimitReachedException;

}
