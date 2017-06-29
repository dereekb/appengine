package com.dereekb.gae.utilities.task;

import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Extension of {@link Task} that can be optionally configured.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 * @param <C>
 *            configuration type
 */
public interface ConfigurableTask<T, C>
        extends Task<T> {

	/**
	 * Performs the task with default configuration.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @throws FailedTaskException
	 *             If the task did not complete successfully.
	 */
	@Override
	public void doTask(T input) throws FailedTaskException;

	/**
	 * Performs the task with configuration.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @param configuration
	 *            Configuration. May be {@code null} depending on the
	 *            implementation.
	 * @throws FailedTaskException
	 *             If the task did not complete successfully.
	 */
	public void doTask(T input,
	                   C configuration)
	        throws FailedTaskException;

}
