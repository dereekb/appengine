package com.dereekb.gae.model.crud.deprecated.function.delegate;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

@Deprecated
public interface UpdateFunctionDelegate<T> {

	/**
	 * Updates the target model with the template model.
	 *
	 * @param template
	 *            Template model to use for updating.
	 * @param target
	 *            Model to be updated.
	 * @return True if the update completed successfully.
	 * @throws UpdateFailureException
	 *             If the update fails.
	 */
	public void update(T template,
	                   T target) throws AttributeFailureException;

}
