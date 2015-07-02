package com.dereekb.gae.utilities.collections.map;

import java.util.Map;

/**
 * Class that uses a map and an optional catch that is returned when a key in the map does not exist.
 *
 * @author dereekb
 */
public class CatchMap<T> {

	protected Map<String, T> map;
	protected T catchAll;
	protected String nullKeyValue = "default";

	public CatchMap() {}

	public CatchMap(Map<String, T> map, T catchAll) {
		this.map = map;
		this.catchAll = catchAll;
	}

	public T getCatchAll() {
		return this.catchAll;
	}

	public void setCatchAll(T catchAll) {
		this.catchAll = catchAll;
	}

	public Map<String, T> getMap() {
		return this.map;
	}

	public void setMap(Map<String, T> map) {
		this.map = map;
	}

	public String getNullKeyValue() {
		return this.nullKeyValue;
	}

	public void setNullKeyValue(String nullKeyValue) {
		this.nullKeyValue = nullKeyValue;
	}

	public T get(String key) {
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

}
