package com.dereekb.gae.utilities.collections.map.catchmap.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link CatchMapImpl} with case-insensitive {@link String} keys.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public class CaseInsensitiveCatchMapImpl<T> extends CatchMapImpl<String, T> {

	public CaseInsensitiveCatchMapImpl() {
		super();
	}

	public CaseInsensitiveCatchMapImpl(T catchAll, Map<String, T> map) {
		super(catchAll, map);
	}

	public CaseInsensitiveCatchMapImpl(T catchAll, String nullKeyValue, Map<String, T> map) {
		super(catchAll, nullKeyValue, map);
	}

	@Override
	public void setMap(Map<String, T> map) {
		super.setMap(new CaseInsensitiveMap<>(map));
	}

	@Override
	public String toString() {
		return "CaseInsensitiveCatchMapImpl [getCatchAll()=" + this.getCatchAll() + ", getMap()=" + this.getMap()
		        + ", getNullKeyValue()=" + this.getNullKeyValue() + "]";
	}

}
