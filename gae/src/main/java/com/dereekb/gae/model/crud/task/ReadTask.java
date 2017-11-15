package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.ConfigurableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Task for reading models into a {@link ReadPair}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadTask<T extends UniqueModel>
        extends ConfigurableTask<Iterable<ReadPair<T>>, AtomicTaskConfig> {

	public void doReadTask(Iterable<ReadPair<T>> pairs,
	                       boolean atomic)
	        throws AtomicOperationException,
	            FailedTaskException;

}
