package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} used for saving.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableUpdateTask<T>
        extends IterableTask<T> {

	public void doUpdateTask(Iterable<T> input) throws FailedTaskException;

	@Deprecated
	public void doUpdateTask(Iterable<T> input,
	                         boolean async)
	        throws FailedTaskException;

}
