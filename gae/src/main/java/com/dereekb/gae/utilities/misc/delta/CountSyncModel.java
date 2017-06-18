package com.dereekb.gae.utilities.misc.delta;

/**
 * Used for models that have a count that much be synchronized with other
 * models.
 * 
 * @author dereekb
 *
 * @param <N>
 *            number type
 * 
 * @see {@link CountSyncModelAccessor}.
 */
public interface CountSyncModel<N extends Number> {

	/**
	 * Current count value.
	 * 
	 * @return Number. Never {@code null}.
	 */
	public N getCount();

	/**
	 * Returns the current unsynchronized "delta" value that represents how much
	 * the count has changed since it was last synchronized.
	 * <p>
	 * Is 0 for newly initialized models. {@code null} is for delta values that
	 * have already been cleared/reset, and have not changed again. This is
	 * to allow a count to be 0, and update properly.
	 * <p>
	 * 
	 * @return Number, or {@code null} if synchronized/no changes.
	 */
	public N getDelta();

}
