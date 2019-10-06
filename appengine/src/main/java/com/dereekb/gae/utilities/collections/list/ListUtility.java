package com.dereekb.gae.utilities.collections.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.filters.SingleObjectFilter;
import com.dereekb.gae.utilities.filters.impl.FilterUtility;

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

	public static <T> List<T> newList(Collection<T> input) {
		List<T> list = new ArrayList<T>();

		if (input != null) {
			list.addAll(input);
		}

		return list;
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

		if (element != null) {
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
	@SafeVarargs
	public static <T> List<T> toList(T... elements) {
		List<T> list = new ArrayList<T>();
		addElements(list, elements);
		return list;
	}

	/**
	 * Adds an element to the input collection if it is not null.
	 *
	 * @param collection
	 * @param newElement
	 */
	public static <T> void addElement(Collection<T> collection,
	                                  T newElement) {
		if (newElement != null) {
			collection.add(newElement);
		}
	}

	/**
	 * Adds the elements from an array to the input list.
	 *
	 * @param collection
	 *            {@link Collection} or {@code null}.
	 * @param elements
	 *            Array of elements to add.
	 * @return {@link Collection}. Never {@code null}.
	 */
	@SafeVarargs
	public static <T> Collection<T> addElements(Collection<T> collection,
	                                            T... elements) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}

		for (T element : elements) {
			collection.add(element);
		}

		return collection;
	}

	/**
	 * Adds the elements from another collection. Always returns a collection.
	 *
	 * @param collection
	 *            {@link Collection} or {@code null}.
	 * @param newElements
	 *            {@link Collection} or {@code null}.
	 * @return {@link Collection}. Never {@code null}.
	 */
	public static <T> Collection<T> addElements(Collection<T> collection,
	                                            Collection<T> newElements) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}

		if (newElements != null) {
			collection.addAll(newElements);
		}

		return collection;
	}

	/**
	 * Checks that the input lists are exactly the same by checking models
	 * order, etc.
	 *
	 * @param a
	 *            {@link List}. Never {@code null}.
	 * @param b
	 *            {@link List}. Never {@code null}.
	 * @return {@code true} if both lists have the same elements and same order.
	 */
	public static <T> boolean checkExactOrder(List<? extends T> a,
	                                          List<? extends T> b) {
		if (a.size() != b.size()) {
			return false;
		} else if (a.size() == 0) {
			return true;
		} else if (a == b) {	// Same list has the same objects.
			return true;
		}

		int count = a.size();

		for (int i = 0; i < count; i += 1) {
			T x = a.get(i);
			T y = b.get(i);

			if (x.equals(y) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates a new list with the input element reference copied n times.
	 * <p>
	 * Example: [1] -> [1,1,...]
	 *
	 * @param element
	 *            element to reference multiple times in the new List.
	 * @param count
	 *            number of times to copy references in new list
	 * @return {@link List}. Never {@code null}.
	 *
	 * @throws IllegalArgumentException
	 *             thrown if the count is not greater than 0.
	 */
	public static <T> List<T> cloneReferences(T element,
	                                          int count)
	        throws IllegalArgumentException {
		if (count < 1) {
			throw new IllegalArgumentException("Count must be greater than 0.");
		}

		List<T> list = new ArrayList<T>(count);

		for (int i = 0; i < count; i += 1) {
			list.add(element);
		}

		return list;
	}

	/**
	 * Creates a new list with the input element references copied n times. The
	 * order is kept, so if the input is [1,2], the result will be [1,2,1,2,...]
	 *
	 * @param elements
	 *            elements to reference multiple times in the new List.
	 * @param count
	 *            number of times to copy references in new list
	 * @return {@link List}. Never {@code null}.
	 *
	 * @throws IllegalArgumentException
	 *             thrown if the count is not greater than 0.
	 */
	public static <T> List<T> cloneReferences(Iterable<? extends T> elements,
	                                          int count)
	        throws IllegalArgumentException {
		if (count < 1) {
			throw new IllegalArgumentException("Count must be greater than 0.");
		}

		List<T> elementsList = IteratorUtility.iterableToList(elements);

		if (count == 1) {
			return elementsList;
		}

		List<T> list = new ArrayList<T>(count * elementsList.size());

		for (int i = 0; i < count; i += 1) {
			list.addAll(elementsList);
		}

		return list;
	}

	/**
	 * Asserts that no object input is null.
	 *
	 * @param components
	 */
	public static void assertNoNulls(Object[] components) throws NullPointerException {
		for (Object component : components) {
			if (component == null) {
				throw new NullPointerException();
			}
		}
	}

	/**
	 * Flattens a two-dimensional array into a single list.
	 *
	 * @param arrays
	 *            Arrays. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	@SafeVarargs
	public static <T> List<T> flatten(T[]... arrays) {
		List<T[]> list = toList(arrays);
		return flattenArrayList(list);
	}

	public static <T> List<T> flattenArrayList(List<T[]> list) {
		List<T> flattened = new ArrayList<T>();

		for (T[] entry : list) {
			flattened.addAll(toList(entry));
		}

		return flattened;
	}

	/**
	 * Flattens a list of lists.
	 *
	 * @param lists
	 *            {@link List}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T> List<T> flatten(List<List<T>> lists) {
		List<T> flattened = new ArrayList<T>();

		for (List<T> list : lists) {
			flattened.addAll(list);
		}

		return flattened;
	}

	/**
	 * Sends an item to the front of the list.
	 * <p>
	 * If it doesn't exist in the list, it is added to the list at the front.
	 *
	 * @param x
	 *            Object. Never {@code null}.
	 * @param list
	 *            {@link List}. Never {@code null}.
	 */
	public static <T> void sendToFront(T x,
	                                   List<T> list) {
		int index = list.indexOf(x);

		if (index == -1) {
			// If not in the list, add it to the front.
			T t = list.get(0);
			list.set(0, x);
			list.add(t);
		} else {
			swap(0, index, list);
		}
	}

	public static <T> void swap(int x,
	                            int y,
	                            List<T> list)
	        throws IndexOutOfBoundsException {
		T ex = list.get(x);
		T ey = list.get(y);

		list.set(x, ey);
		list.set(y, ex);
	}

	public static <T> T getLastElement(Collection<T> collection) {
		if (collection.isEmpty()) {
			return null;
		} else {
			List<T> list = ListUtility.newList(collection);
			return getLastElement(list);
		}
	}

	public static <T> T getLastElement(List<T> list) {
		return getElementAtIndex(list, list.size() - 1);
	}

	public static <T> T getElementAtIndex(List<T> list,
	                                      int index) {
		try {
			return list.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static boolean isLastElementIndex(int index,
	                                         String[] array) {
		return index == array.length - 1;
	}

	public static boolean isLastElement(int index,
	                                    List<?> list) {
		return index == list.size() - 1;
	}

	/**
	 * Filters the iterables to a list using the input filter.
	 *
	 * @param iterable
	 *            Iterable. Never {@code null}.
	 * @param filter
	 *            Filter. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T> List<T> filter(Iterable<? extends T> iterable,
	                                 SingleObjectFilter<T> filter) {
		FilterResults<T> results = FilterUtility.filterObjects(filter, iterable);
		return results.getPassingObjects();
	}

	/**
	 * Converts the collection to an array.
	 *
	 * @param type
	 *            Class type. Never {@code null}.
	 * @param collection
	 *            Collection, or {@code null} is allowed.
	 * @return Array, or {@code null} if the collection was null.
	 */
	public static <T> T[] toArray(Class<T> type,
	                              Collection<T> collection) {
		if (collection == null) {
			return null;
		}

		int size = collection.size();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(type, size);
		return collection.toArray(array);
	}
}
