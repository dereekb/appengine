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
public interface IterableSaveTask<T>
        extends IterableTask<T> {

	public void doSaveTask(Iterable<T> input) throws FailedTaskException;

	public void doSaveTask(Iterable<T> input,
	                       boolean async)
	        throws FailedTaskException;

}
