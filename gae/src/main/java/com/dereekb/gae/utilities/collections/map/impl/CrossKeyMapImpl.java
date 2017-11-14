package com.dereekb.gae.utilities.collections.map.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.map.CrossKeyMap;

/**
 * {@link CrossKeyMap} implementation.
 * 
 * @author dereekb
 * 
 * @param <X>
 *            x key type
 * @param <Y>
 *            y key type
 */
public class CrossKeyMapImpl<X, Y>
        implements CrossKeyMap<X, Y> {

	/**
	 * X keys, Y values
	 */
	private Map<X, Y> y = new HashMap<X, Y>();

	/**
	 * Y keys, X values
	 */
	private Map<Y, X> x = new HashMap<Y, X>();

	// MARK: Get
	@Override
	public X getX(Y key) {
		return this.x.get(key);
	}

	@Override
	public Y getY(X key) {
		return this.y.get(key);
	}

	@Override
	public Set<X> allX() {
		return this.y.keySet();
	}

	@Override
	public Set<Y> allY() {
		return this.x.keySet();
	}

	// MARK: Put
	@Override
	public void put(X xKey,
	                Y yKey) throws IllegalArgumentException {
		if (xKey == null || yKey == null) {
			throw new IllegalArgumentException("Cannot put a null key.");
		}
		
		this.y.put(xKey, yKey);
		this.x.put(yKey, xKey);
	}

	// MARK: Contains
	@Override
	public boolean containsX(X xKey) {
		return this.y.containsKey(xKey);
	}

	@Override
	public boolean containsY(Y yKey) {
		return this.x.containsKey(yKey);
	}

	@Override
	public String toString() {
		return "CrossKeyMap [y=" + this.y + ", x=" + this.x + "]";
	}

}
