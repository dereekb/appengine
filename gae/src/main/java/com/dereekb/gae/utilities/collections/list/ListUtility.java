package com.dereekb.gae.utilities.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListUtility {

	public static <T> List<T> copy(Collection<? extends T> input) {
		return new ArrayList<T>(input);
	}

	/**
	 * Safe copies the values from the collection into an array, only if the
	 * input is not null. Otherwise returns null.
	 * 
	 * @param input
	 * @return
	 */
	public static <T> List<T> safeCopy(Collection<? extends T> input) {
		if (input != null) {
			return new ArrayList<T>(input);
		} else {
			return null;
		}
	}

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
			list = new ArrayList<T>(1);
			list.add(element);
		} else {
			list = Collections.emptyList();
		}

		return list;
	}

	/**
	 * Converts the array elements to a list.
	 *
	 * @param elements
	 *            Array of elements to add.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T> List<T> toList(T[] elements) {
		List<T> list = new ArrayList<T>();
		addElements(list, elements);
		return list;
	}

	/**
	 * Adds the elements from an array to the input list.
	 *
	 * @param list
	 *            {@link List} or {@code null}.
	 * @param elements
	 *            Array of elements to add.
	 * @return {@link Collection}. Never {@code null}.
	 */
	public static <T> Collection<T> addElements(Collection<T> list,
	                                            T[] elements) {
		if (list == null) {
			list = new ArrayList<T>();
		}

		for (T element : elements) {
			list.add(element);
		}

		return list;
	}

}
