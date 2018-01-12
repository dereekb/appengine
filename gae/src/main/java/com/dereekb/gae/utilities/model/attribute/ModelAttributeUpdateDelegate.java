package com.dereekb.gae.utilities.model.attribute;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Update delegate for a {@link ModelAttributeHelperUtilityInstance}.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ModelAttributeUpdateDelegate<T> {

	/**
	 * Modifies the value.
	 *
	 * @throws InvalidAttributeException
	 *             thrown if the update fails.
	 */
	public void modifyValue(T value) throws InvalidAttributeException;

}
