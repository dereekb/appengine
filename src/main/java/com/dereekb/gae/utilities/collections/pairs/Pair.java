package com.dereekb.gae.utilities.collections.pairs;

import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * A key/object pair.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 * 
 * @see HandlerPair
 */
public interface Pair<K, T> {

	/**
	 * Returns the pair key.
	 * 
	 * @return Model. Never {@code null}.
	 */
	public K getKey();

	/**
	 * Returns the pair object.
	 * 
	 * @return Model. Can be {@code null}.
	 */
	public T getObject();

}
