package com.dereekb.gae.utilities.collections.map.catchmap.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.catchmap.CatchMap;

/**
 * {@link CatchMap} implementation.
 *
 * @author dereekb
 */
public class CatchMapImpl<K, T>
        implements CatchMap<K, T> {

	private T catchAll;
	private K nullKeyValue;

	private Map<K, T> map;

	public CatchMapImpl() {}

	public CatchMapImpl(T catchAll, Map<K, T> map) {
		this.setMap(map);
		this.setCatchAll(catchAll);
	}

	public CatchMapImpl(T catchAll, K nullKeyValue, Map<K, T> map) {
		this.setMap(map);
		this.setCatchAll(catchAll);
		this.setNullKeyValue(nullKeyValue);
	}

	@Override
	public T getCatchAll() {
		return this.catchAll;
	}

	@Override
    public void setCatchAll(T catchAll) {
		this.catchAll = catchAll;
	}

	public Map<K, T> getMap() {
		return this.map;
	}

	public void setMap(Map<K, T> map) {
		this.map = map;
	}

	@Override
	public K getNullKeyValue() {
		return this.nullKeyValue;
	}

	@Override
    public void setNullKeyValue(K nullKeyValue) {
		this.nullKeyValue = nullKeyValue;
	}

	@Override
    public T get(K key) {
		T delegate = null;

		if (key == null) {
			key = this.nullKeyValue;
		}

		if (this.map != null) {
			delegate = this.map.get(key);
		}

		if (delegate == null) {
			delegate = this.catchAll;
		}

		return delegate;
	}

	@Override
	public String toString() {
		return "CatchMapImpl [catchAll=" + this.catchAll + ", nullKeyValue=" + this.nullKeyValue + ", map=" + this.map
		        + "]";
	}

}
