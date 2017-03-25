package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} used for storing new models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableDeleteTask<T>
        extends IterableTask<T> {

	public void doDeleteTask(Iterable<T> input) throws FailedTaskException;

}
