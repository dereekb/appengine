package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.UpdateTaskImpl;

/**
 * {@link UpdateTaskImpl} delegate.
 * <p>
 * Is used for updating a target model using an input template.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateTaskDelegate<T> {

	/**
	 * Updates the {@code target} with the {@code template} model.
	 *
	 * @param template
	 *            Template model to use for updating. Never {@code null}.
	 * @param target
	 *            Model to be updated. Never {@code null}.
	 * @throws AttributeFailureException
	 *             If the update fails.
	 */
	public void updateTarget(T target,
	                         T template) throws AttributeFailureException;

}
