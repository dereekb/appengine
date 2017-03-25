package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.server.datastore.task.IterableDeleteTask;
import com.dereekb.gae.server.datastore.task.IterableStoreTask;
import com.dereekb.gae.server.datastore.task.IterableUpdateTask;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} extension that allows overriding the async.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated Use the more specific tasks directly.
 */
@Deprecated
public interface IterableSetterTask<T>
        extends IterableDeleteTask<T>, IterableUpdateTask<T>, IterableStoreTask<T> {

	@Deprecated
	public void doTask(Iterable<T> input,
	                   boolean async)
	        throws FailedTaskException;

}
