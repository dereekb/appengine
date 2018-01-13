package com.dereekb.gae.utilities.model.attribute;

/**
 * Helper utility for a specific type of value.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ModelAttributeUtilityBuilder<T> {

	/**
	 * Whether or not the value provided is a change value or should be ignored.
	 * <p>
	 * In some cases values passed may null, or another value that should be
	 * ignored.
	 *
	 * @param value
	 *            Input value. Can be {@code null}.
	 * @return {@code true} if the value should be changed.
	 */
	public boolean isChangeValue(T value);

	/**
	 * Returns the "real" value decoded from the input.
	 *
	 * @param value
	 *            Input value. Never {@code null}.
	 * @return value. Can be {@code null}.
	 */
	public T getDecodedValue(T value);

	/**
	 * Returns a new {@link ModelAttributeUtilityInstance} for an
	 * attribute.
	 *
	 * @param attribute
	 *            Attribute name. Never {@code null}.
	 * @return {@link ModelAttributeUtilityInstance}. Never {@code null}.
	 */
	public ModelAttributeUtilityInstance<T> makeInstance(String attribute) throws UnsupportedOperationException;

	/**
	 * Returns a new {@link ModelAttributeUtilityInstance} for an
	 * attribute.
	 *
	 * @param attribute
	 *            Attribute name. Never {@code null}.
	 * @param delegate
	 *            Delegate. May be null depending on the implementation.
	 * @return {@link ModelAttributeUtilityInstance}. Never {@code null}.
	 * @throws UnsupportedOperationException
	 *             thrown if the delegate is null but is required.
	 */
	public ModelAttributeUtilityInstance<T> makeInstance(String attribute,
	                                                     ModelAttributeUpdateDelegate<T> delegate)
	        throws UnsupportedOperationException;

}
