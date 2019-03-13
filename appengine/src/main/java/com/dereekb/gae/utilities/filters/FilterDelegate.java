package com.dereekb.gae.utilities.filters;

/**
 * 
 * @author dereekb
 *
 * @param <T> Object type
 * @param <U> Source type that contains an object T.
 */
public interface FilterDelegate<T, U> {

	/**
	 * 
	 * @param source
	 *            Source to read the model from
	 * @return Returns an object from the source given.
	 */
	public T getModel(U source);
	
}
