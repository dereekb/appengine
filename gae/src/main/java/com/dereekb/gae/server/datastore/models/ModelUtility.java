package com.dereekb.gae.server.datastore.models;

import java.util.HashMap;
import java.util.Map;

public class ModelUtility {

	/**
	 * Makes a {@link Map} from {@link TypedModel} values.
	 * 
	 * @param values
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public static <T extends TypedModel> Map<String, T> makeTypedModelMap(Iterable<T> values) {
		Map<String, T> map = new HashMap<String, T>();

		for (T value : values) {
			String key = value.getModelType();
			map.put(key, value);
		}

		return map;
	}

}
