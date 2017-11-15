package com.dereekb.gae.utilities.collections.map;

import java.util.Set;

/**
 * Special map collection that holds two maps and can reference a specific type
 * in either direction.
 * <p>
 * Generally both input types are keys/unique.
 * 
 * @author dereekb
 * 
 * @param <X>
 *            x key type
 * @param <Y>
 *            y key type
 */
public interface CrossKeyMap<X, Y> {

	// MARK: Get
	/**
	 * @param key
	 *            Key. Never {@code null}.
	 * @return Key, or {@code null} if not in the map.
	 */
	public X getX(Y key);

	/**
	 * @param key
	 *            Key. Never {@code null}.
	 * @return Key, or {@code null} if not in the map.
	 */
	public Y getY(X key);

	// MARK: All
	public Set<X> allX();

	public Set<Y> allY();

	// MARK: Contains
	/**
	 * Checks if the x key exists.
	 * 
	 * @param xKey
	 *            Key. Never {@code null}.
	 * @return {@code true} if contained.
	 */
	public boolean containsX(X xKey);

	/**
	 * Checks if the y key exists.
	 * 
	 * @param yKey
	 *            Key. Never {@code null}.
	 * @return {@code true} if contained.
	 */
	public boolean containsY(Y yKey);

}
