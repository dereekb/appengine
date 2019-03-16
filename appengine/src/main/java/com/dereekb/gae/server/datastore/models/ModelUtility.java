package com.dereekb.gae.server.datastore.models;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

public class ModelUtility {

	/**
	 * Makes a {@link Map} from {@link TypedModel} values.
	 *
	 * @param values
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public static <T extends TypedModel> CaseInsensitiveMap<T> makeTypedModelMap(Iterable<T> values) {
		CaseInsensitiveMap<T> map = new CaseInsensitiveMap<T>();

		for (T value : values) {
			String key = value.getModelType();
			map.put(key, value);
		}

		return map;
	}

}
