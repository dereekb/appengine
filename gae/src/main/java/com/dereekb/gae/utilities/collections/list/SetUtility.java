package com.dereekb.gae.utilities.collections.list;

import java.util.HashSet;
import java.util.Set;

public class SetUtility {

	@SafeVarargs
	public static <T> Set<T> makeSet(T... objects) {
		Set<T> set = new HashSet<T>();

		for (T object : objects) {
			set.add(object);
		}

		return set;
	}

}
