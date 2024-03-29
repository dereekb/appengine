package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.task.impl.UpdateTaskImpl;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link UpdateTaskImpl} delegate.
 * <p>
 * Is used for updating a target model using an input template. The update
 * should be idempotent.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateTaskDelegate<T> {

	/**
	 * Updates the {@code target} with the {@code template} model.
	 * <p>
	 * The change should be idempotent.
	 *
	 * @param template
	 *            Template model to use for updating. Never {@code null}.
	 * @param target
	 *            Model to be updated. Never {@code null}.
	 * @throws InvalidAttributeException
	 *             If the update fails.
	 */
	public void updateTarget(T target,
	                         T template)
	        throws InvalidAttributeException;

}
