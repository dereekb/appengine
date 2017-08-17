package com.dereekb.gae.utilities.collections.pairs;

/**
 * Mutable {@link SuccessPair}.
 * 
 * @author dereekb
 *
 * @param <S> source model type
 */
public interface MutableSuccessPair<S> extends SuccessPair<S>, MutableResultPair<S, Boolean> {

	/**
	 * Same as {@link #setResult(Object)}.
	 * 
	 * @param successful Boolean.
	 */
	public void setSuccessful(boolean successful);
	
}
