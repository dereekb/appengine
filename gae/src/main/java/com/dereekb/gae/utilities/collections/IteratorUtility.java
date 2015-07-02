package com.dereekb.gae.utilities.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

}
