package com.dereekb.gae.utilities.misc.delta;

/**
 * Used as a wrapper for modifying the count and delta on a
 * {@link MutableCountSyncModel}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <N>
 *            count number type
 */
public interface CountSyncModelAccessor<T, N extends Number>
        extends CountSyncModelReader<T, N> {

	/**
	 * Initializes the count on the model.
	 */
	public void initCount();

	/**
	 * Sets a new count.
	 * 
	 * @param count
	 *            Count. Can be {@code null}.
	 */
	public void setCount(N count);

	/**
	 * Sets the delta equal to the current count.
	 */
	public void resetDeltaToCount();

	/**
	 * Clears the current delta.
	 */
	public void clearDelta();

}
