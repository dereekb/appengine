package com.dereekb.gae.utilities.collections;

import java.util.ArrayList;
import java.util.Collections;
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
		Iterator<T> iterator = null;

		if (iterable != null) {
			iterator = iterable.iterator();
		}

		return iteratorToList(iterator);
	}

	public static <T> List<T> iteratorToList(Iterator<T> iterator) {
		List<T> list;

		if (iterator != null) {
			list = new ArrayList<T>();

			while (iterator.hasNext()) {
				T element = iterator.next();
				list.add(element);
			}
		} else {
			list = Collections.emptyList();
		}

		return list;
	}

	public static <T> Set<T> iterableToSet(Iterable<T> iterable) {
		Iterator<T> iterator = null;

		if (iterable != null) {
			iterator = iterable.iterator();
		}

		return iteratorToSet(iterator);
	}

	public static <T> Set<T> iteratorToSet(Iterator<T> iterator) {
		Set<T> set;

		if (iterator != null) {
			set = new HashSet<T>();

			while (iterator.hasNext()) {
				T element = iterator.next();
				set.add(element);
			}
		} else {
			set = Collections.emptySet();
		}

		return set;
	}

}
