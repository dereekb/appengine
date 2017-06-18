package com.dereekb.gae.utilities.misc.delta;

/**
 * Used for wrapping a {@link CountSyncModel} to provide additional information
 * about the current state.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <N>
 *            count number type
 */
public interface CountSyncModelReader<T, N extends Number>
        extends CountSyncModel<N> {

	/**
	 * Returns the current synchronized count that does not include the current
	 * delta.
	 * 
	 * @return {@link Long}. Never {@code null}.
	 */
	public N getSynchronizedCount();

	/**
	 * Returns the model the accessor is modifying.
	 * 
	 * @return Model. Never {@code null}.
	 */
	public T getModel();

	/**
	 * Whether or not the delta exists or not.
	 * 
	 * @return {@code true} if a change exists.
	 */
	public boolean hasDeltaChange();

	/**
	 * Returns the delta as a number. If no delta available, will return the
	 * default delta (usually 0).
	 * 
	 * @return Number value. Never {@code null}.
	 */
	public N getNonNullDelta();

}
