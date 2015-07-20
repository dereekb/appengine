package com.dereekb.gae.utilities.task;

/**
 * A basic task that takes in input.
 *
 * @author dereekb
 *
 * @param <T>
 *            Task input
 */
public interface Task<T> {

	/**
	 * Performs the task.
	 *
	 * @param input
	 *            Task input. Never {@code null}.
	 */
	public void doTask(T input);

}
