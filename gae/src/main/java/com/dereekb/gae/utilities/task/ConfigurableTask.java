package com.dereekb.gae.utilities.task;

/**
 * Extension of {@link Task} that can be optionally configured.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type.
 * @param <C>
 *            Configuration type.
 */
public interface ConfigurableTask<T, C>
        extends Task<T> {

	/**
	 * Performs the task with default configuration.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 */
	@Override
	public void doTask(T input);

	/**
	 * Performs the task with configuration.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 * @param configuration
	 *            Configuration. Never {@code null}.
	 */
	public void doTask(T input,
	                   C configuration);

}
