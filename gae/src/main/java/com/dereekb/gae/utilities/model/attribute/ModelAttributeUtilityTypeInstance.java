package com.dereekb.gae.utilities.model.attribute;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Configured {@link ModelAttributeUtilityInstance} instance for a specific
 * type.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 * @param <M>
 *            model type
 */
public interface ModelAttributeUtilityTypeInstance<T, M> {

	/**
	 * Attempts to update the model.
	 *
	 * @throws InvalidAttributeException
	 *             thrown if the update fails.
	 */
	public void tryUpdateValue(T input,
	                           M model)
	        throws InvalidAttributeException;

}
