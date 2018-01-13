package com.dereekb.gae.utilities.model.attribute;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link ModelAttributeUtilityBuilder} instance for a specific attribute.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ModelAttributeUtilityInstance<T>
        extends ModelAttributeUtilityBuilder<T> {

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
	 * Attempts to update the value.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if the operation isn't available.
	 * @throws InvalidAttributeException
	 *             thrown if the update fails.
	 */
	public void updateValue(T input) throws UnsupportedOperationException, InvalidAttributeException;

	/**
	 * Creates a type instance.
	 *
	 * @return [@link ModelAttributeUtilityTypeInstance}. Never {@code null}.
	 */
	public <M> ModelAttributeUtilityTypeInstance<T, M> typeInstance(ModelAttributeTypeUpdateDelegate<T, M> delegate);

}
