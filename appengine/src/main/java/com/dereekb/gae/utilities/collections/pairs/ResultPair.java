package com.dereekb.gae.utilities.collections.pairs;

/**
 * {@link Pair} extension that has a {@link ResultPairState}.
 * 
 * @author dereekb
 *
 * @param <S>
 *            source model type
 * @param <R>
 *            result model type
 */
public interface ResultPair<S, R>
        extends Pair<S, R> {

	/**
	 * Returns the pair state.
	 * 
	 * @return {@link ResultPairState}. Never {@code null}.
	 */
	public ResultPairState getState();

	/**
	 * Whether or not this has a result value.
	 * 
	 * @return {@code true}.
	 */
	public boolean hasResult();

}
