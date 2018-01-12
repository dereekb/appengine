package com.dereekb.gae.utilities.model.attribute;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link ModelAttributeHelperUtility} instance for a specific attribute.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ModelAttributeHelperUtilityInstance<T>
        extends ModelAttributeHelperUtility<T> {

	/**
	 * Returns the attribute's name. Used for errors, etc.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAttribute();

	/**
	 * Assert valid decoded value. Assumes the value has been through
	 * {{@link #getDecodedValue(Object)}.
	 *
	 * @param value
	 *            Decoded value. Can be {@code null}.
	 * @throws InvalidAttributeException
	 *             thrown if the value is deemed invalid or illegal.
	 */
	public void assertValidDecodedValue(T value) throws InvalidAttributeException;

	/**
	 * Attempts to update the value using a delegate.
	 *
	 * @throws InvalidAttributeException
	 *             thrown if the update fails.
	 */
	public void tryUpdateValue(T input) throws InvalidAttributeException;

}
