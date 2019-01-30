package com.dereekb.gae.server.datastore.task;

import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} used for updating models.
 * <p>
 * Generally wraps an {@link Updater}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableUpdateTask<T> {

	/**
	 * Updates the input models.
	 * 
	 * @param input
	 *            {@link Iterable}. Never {@code null}.
	 * 
	 * @throws FailedTaskException
	 *             thrown if one or more cannot be updated.
	 */
	public void doUpdateTask(Iterable<T> input) throws FailedTaskException;

}
