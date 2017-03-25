package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} used for updating models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableUpdateTask<T>
        extends IterableTask<T> {

	public void doUpdateTask(Iterable<T> input) throws FailedTaskException;

}
