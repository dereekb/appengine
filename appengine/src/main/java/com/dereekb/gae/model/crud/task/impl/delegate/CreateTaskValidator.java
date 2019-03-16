package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.impl.ValidatedCreateTaskImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * Validation task for a {@link ValidatedCreateTaskImpl}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CreateTaskValidator<T extends UniqueModel>
        extends IterableTask<CreatePair<T>> {

	/**
	 * {@inheritDoc}
	 * 
	 * Validates the input {@link CreatePair} values, and updates them if any
	 * are invalid.
	 * 
	 * @throws AtomicOperationException
	 *             thrown if one or more values are deemed unfit.
	 */
	@Override
	public void doTask(Iterable<CreatePair<T>> input) throws AtomicOperationException;

}
