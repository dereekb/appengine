package com.dereekb.gae.utilities.collections.pairs;

/**
 * Mutable {@link ResultPair}.
 * 
 * @author dereekb
 *
 * @param <S>
 *            source model type
 * @param <R>
 *            result model type
 */
public interface MutableResultPair<S, R>
        extends ResultPair<S, R> {

	/**
	 * Sets a result and updates the internal pair state.
	 * 
	 * @param result Result. May be {@code null}.
	 */
	public void setResult(R result);

	/**
	 * Sets the pair state to {@link ResultsPairState#FAILURE}.
	 */
	public void flagFailure();

	/**
	 * Clears the result and sets the pair state to {@link ResultsPairState#CLEARED}.
	 */
	public void clearResult();

}
