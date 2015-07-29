package com.dereekb.gae.model.crud.deprecated.function.delegate;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;

public interface UpdateFunctionDelegate<T> {

	/**
	 * Updates the target model with the template model.
	 *
	 * @param template
	 *            Template model to use for updating.
	 * @param target
	 *            Model to be updated.
	 * @return True if the update completed successfully.
	 * @throw UpdateFailureException If the update fails.
	 */
	public void update(T template,
	                   T target) throws AttributeFailureException;

}
