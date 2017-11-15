package com.dereekb.gae.utilities.collections.map;

import java.util.Map;

/**
 * Mutable {@link CrossKeyMap}.
 * 
 * @author dereekb
 * 
 * @param <X>
 *            x key type
 * @param <Y>
 *            y key type
 */
public interface MutableCrossKeyMap<X, Y>
        extends CrossKeyMap<X, Y> {

	// MARK: Put
	/**
	 * Puts the keys into the map.
	 * 
	 * @param xKey
	 *            Key. Never {@code null}.
	 * @param yKey
	 *            Key. Never {@code null}.
	 */
	public void put(X xKey,
	                Y yKey)
	        throws IllegalArgumentException;

	/**
	 * Clears the map.
	 */
	public void clear();

	// MARK: Set
	public void setXMap(Map<Y, X> x);

	public void setYMap(Map<X, Y> y);

}
