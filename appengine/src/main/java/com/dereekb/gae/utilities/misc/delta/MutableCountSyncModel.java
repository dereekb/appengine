package com.dereekb.gae.utilities.misc.delta;

/**
 * Mutable {@link CountSyncModel}.
 * <p>
 * This type should <b>ONLY</b> be modified using a
 * {@link CountSyncModelAccessorFactory} implementation.
 * 
 * @author dereekb
 *
 * @param <N>
 *            count number type
 */
public interface MutableCountSyncModel<T, N extends Number>
        extends CountSyncModel<N> {

	/**
	 * Creates a new count accessor for this model.
	 * 
	 * @return
	 */
	public CountSyncModelAccessor<T, N> getCountAccessor();

	/**
	 * Sets the count directly.
	 * 
	 * @see #getCountAccessor()
	 */
	public void setRawCount(N count);

	/**
	 * Sets the delta directly.
	 * 
	 * @see #getCountAccessor()
	 */
	public void setRawDelta(N delta);

}
