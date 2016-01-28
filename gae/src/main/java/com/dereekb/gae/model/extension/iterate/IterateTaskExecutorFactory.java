package com.dereekb.gae.model.extension.iterate;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;


/**
 * Used for running tasks over large sets of models.
 *
 * @author dereekb
 *
 * @param <T>
 *            input type
 */
public interface IterateTaskExecutorFactory<T extends UniqueModel> {

	/**
	 * Creates a new {@link IterateTaskExecutor} containing the input task.
	 *
	 * @param task
	 *            {@link IterableTask} to wrap. Never {@code null}.
	 * @return {@link IterateTaskExecutor}. Never {@code null}.
	 */
	public IterateTaskExecutor<T> makeExecutor(IterableTask<T> task);

}
