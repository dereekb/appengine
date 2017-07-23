package com.dereekb.gae.utilities.collections.pairs;

/**
 * {@link ResultPair} with a boolean as a result.
 * 
 * @author dereekb
 *
 * @param <S>
 *            source model type
 */
public interface SuccessPair<S>
        extends ResultPair<S, Boolean> {

	/**
	 * Convenience function for {@link #getObject()}.
	 * 
	 * @return {@code true} if successful.
	 */
	public boolean isSuccessful();

}
