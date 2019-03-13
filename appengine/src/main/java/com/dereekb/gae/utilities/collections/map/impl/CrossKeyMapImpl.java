package com.dereekb.gae.utilities.collections.map.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.utilities.collections.map.CrossKeyMap;
import com.dereekb.gae.utilities.collections.map.MutableCrossKeyMap;

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
        implements MutableCrossKeyMap<X, Y> {

	/**
	 * X keys, Y values
	 */
	private Map<X, Y> y;

	/**
	 * Y keys, X values
	 */
	private Map<Y, X> x;

	public CrossKeyMapImpl() {
		this.clear();
	}

	public static <X, Y> CrossKeyMapImpl<X, Y> makeX(Map<Y, X> x) {
		CrossKeyMapImpl<X, Y> map = new CrossKeyMapImpl<X, Y>();
		map.setXMap(x);
		return map;
	}

	public static <X, Y> CrossKeyMapImpl<X, Y> makeY(Map<X, Y> x) {
		CrossKeyMapImpl<X, Y> map = new CrossKeyMapImpl<X, Y>();
		map.setYMap(x);
		return map;
	}

	@Override
	public void clear() {
		if (this.x == null || this.x.isEmpty() == false) {
			this.y = new HashMap<X, Y>();
			this.x = new HashMap<Y, X>();
		}
	}

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
	                Y yKey)
	        throws IllegalArgumentException {
		if (xKey == null || yKey == null) {
			throw new IllegalArgumentException("Cannot put a null key.");
		}

		this.y.put(xKey, yKey);
		this.x.put(yKey, xKey);
	}

	@Override
	public void setXMap(Map<Y, X> x) {
		for (Entry<Y, X> entry : x.entrySet()) {
			this.put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public void setYMap(Map<X, Y> y) {
		for (Entry<X, Y> entry : y.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
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
