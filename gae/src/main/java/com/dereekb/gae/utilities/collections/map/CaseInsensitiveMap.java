package com.dereekb.gae.utilities.collections.map;

import java.util.Map;
import java.util.TreeMap;

public class CaseInsensitiveMap<T> extends TreeMap<String, T> {

	private static final long serialVersionUID = 1L;

	public CaseInsensitiveMap() {
		super(String.CASE_INSENSITIVE_ORDER);
	}

	public CaseInsensitiveMap(Map<? extends String, ? extends T> m) {
		this();

		if (m != null) {
			this.putAll(m);
		}
	}

	@Override
    public String toString() {
		return "CaseInsensitiveMap [toString()=" + super.toString() + "]";
    }

}
