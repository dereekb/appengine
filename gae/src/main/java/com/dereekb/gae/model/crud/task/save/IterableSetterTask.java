package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} extension that allows overriding the async.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated use {@link IterableUpdateTask} directly.
 */
@Deprecated
public interface IterableSetterTask<T>
        extends IterableUpdateTask<T> {

	@Deprecated
	public void doTask(Iterable<T> input,
	                   boolean async)
	        throws FailedTaskException;

}
