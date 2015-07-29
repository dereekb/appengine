package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link IterableTask} for creating models using {@link UpdatePair} instances.
 *
 * @author dereekb
 *
 */
public interface UpdatePairTask<T extends UniqueModel>
        extends IterableTask<UpdatePair<T>> {

	/**
	 * Performs the update task.
	 *
	 * @param input
	 *            {@link UpdatePair} instances as input. Never {@code null}.
	 * @throws AtomicOperationException
	 *             If a model did not update properly.
	 */
	@Override
	public void doTask(Iterable<UpdatePair<T>> input) throws AtomicOperationException;

}
