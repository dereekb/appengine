package com.dereekb.gae.utilities.model.attribute;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Update delegate for a {@link ModelAttributeUtilityTypeInstance}.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 * @param <M>
 *            model type
 */
public interface ModelAttributeTypeUpdateDelegate<T, M> {

	/**
	 * Modifies the value on the input model.
	 *
	 * @throws InvalidAttributeException
	 *             thrown if the update fails.
	 */
	public void modifyValue(T value,
	                        M model)
	        throws InvalidAttributeException;

}
