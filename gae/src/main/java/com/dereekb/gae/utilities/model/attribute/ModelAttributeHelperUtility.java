package com.dereekb.gae.utilities.model.attribute;

/**
 * Helper utility for a specific type of value.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ModelAttributeHelperUtility<T> {

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
	 * Returns a new {@link ModelAttributeHelperUtilityInstance} for an
	 * attribute.
	 *
	 * @param attribute
	 *            Attribute name. Never {@code null}.
	 * @return {@link ModelAttributeHelperUtilityInstance}. Never {@code null}.
	 */
	public ModelAttributeHelperUtilityInstance<T> makeInstance(String attribute,
	                                                           ModelAttributeUpdateDelegate<T> delegate);

}
