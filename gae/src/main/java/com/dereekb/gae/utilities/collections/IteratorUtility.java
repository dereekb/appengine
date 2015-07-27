package com.dereekb.gae.utilities.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Utility for {@link Iterable} values.
 *
 * @author dereekb
 *
 */
public class IteratorUtility {

	public static <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> list = new ArrayList<T>();

		if (iterable != null) {
			Iterator<T> iterator = iterable.iterator();

			while (iterator.hasNext()) {
				T element = iterator.next();
				list.add(element);
			}
		}

		return list;
	}

	public static <T> Set<T> iterableToSet(Iterable<T> iterable) {
		Set<T> set = new HashSet<T>();

		if (iterable != null) {
			Iterator<T> iterator = iterable.iterator();

			while (iterator.hasNext()) {
				T element = iterator.next();
				set.add(element);
			}
		}

		return set;
	}

}
