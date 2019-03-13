package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.utilities.task.ConfigurableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Atomic {@link ConfigurableTask}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            input type
 * @param <C>
 *            configuration type
 */
public interface AtomicTask<T, C>
        extends ConfigurableTask<Iterable<T>, C> {

	/**
	 * {@inheritDoc}
	 * 
	 * No changes are saved on failure.
	 * 
	 * @throws AtomicOperationException
	 *             if configured as atomic and one or more input elements is
	 *             invalid. No changes are saved.
	 */
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException, AtomicOperationException;

	/**
	 * {@inheritDoc}
	 * 
	 * No changes are saved on failure.
	 * 
	 * @throws AtomicOperationException
	 *             if configured as atomic and one or more input elements is
	 *             invalid.
	 */
	@Override
	public void doTask(Iterable<T> input,
	                   C configuration)
	        throws FailedTaskException,
	            AtomicOperationException;

}
