package com.dereekb.gae.utilities.collections.map;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 *
 * Case insensitive {@link Map} implemented with {@link TreeMap}.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
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

	public static <T> CaseInsensitiveMap<CaseInsensitiveMap<T>> makeNestedMap(Map<String, ? extends Map<String, ? extends T>> input) {
		CaseInsensitiveMap<CaseInsensitiveMap<T>> map = new CaseInsensitiveMap<CaseInsensitiveMap<T>>();

		for (Entry<String, ? extends Map<String, ? extends T>> entry : input.entrySet()) {
			String key = entry.getKey();
			Map<String, ? extends T> entryMap = entry.getValue();
			
			CaseInsensitiveMap<T> subMap = new CaseInsensitiveMap<T>(entryMap); 
			map.put(key, subMap);
		}
		
		return map;
	}
	
	@Override
	public CaseInsensitiveSet keySet() {
		return new CaseInsensitiveSet(super.keySet());
	}

	@Override
	public String toString() {
		return "CaseInsensitiveMap [toString()=" + super.toString() + "]";
	}

}
