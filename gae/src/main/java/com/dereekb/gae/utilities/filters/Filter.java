package com.dereekb.gae.utilities.filters;

/**
 * Used for filtering values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Filter<T> {

	/**
	 * Filters a single object.
	 *
	 * @param object
	 *            Object to filter.
	 * @return FilterResult of either PASS or FAIL depending on if the object
	 *         passed.
	 */
	public FilterResult filterObject(T object);

	/**
	 * Filters a collection of objects.
	 *
	 * @param objects
	 *            Objects to filter.
	 * @return {@link FilterResults} mapping of the results.
	 */
	public FilterResults<T> filterObjects(Iterable<? extends T> objects);

}
