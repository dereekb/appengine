package com.dereekb.gae.utilities.task.iteration.impl;

import java.util.List;

/**
 * {@code ConfiguredIterationTaskImpl} delegate. Is notified when the task
 * starts/ends, and is passed batches of models as opposed to one at a time for
 * convenience purposes.
 *
 * @author dereekb
 *
 */
public interface ConfiguredIterationTaskImplDelegate<T, C> {

	/**
	 * Called before {@link #useInputBatch(List)} is called for the first time.
	 *
	 * @param configuration
	 *            Configuration used to configure this delegate.
	 */
	public void startTask(C configuration);

	/**
	 * Uses a batch of input models.
	 *
	 * @param batch
	 *            {@link List} of input. Never {@code null}.
	 */
	public void useInputBatch(List<T> batch);

	/**
	 * Called after {@link #useInputBatch(List)} is called for the last time.
	 */
	public void endTask();

}
