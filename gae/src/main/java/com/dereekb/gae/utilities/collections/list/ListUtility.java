package com.dereekb.gae.utilities.collections.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtility {

	/**
	 * Wraps the input element. If it is null, returns an empty list created via
	 * {@link java.util.Collections#emptyList()}.
	 *
	 * @param element
	 * @return A new {@link List}. Never null.
	 */
	public static <T> List<T> wrap(T element) {
		List<T> list;

		if (element == null) {
			list = new ArrayList<T>();
			list.add(element);
		} else {
			list = Collections.emptyList();
		}

		return list;
	}

}
