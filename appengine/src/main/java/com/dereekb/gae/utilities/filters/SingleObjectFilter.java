package com.dereekb.gae.utilities.filters;

/**
 * Used for filtering a value.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SingleObjectFilter<T> {

	/**
	 * Filters a single object.
	 *
	 * @param object
	 *            Object to filter.
	 * @return FilterResult of either PASS or FAIL depending on if the object
	 *         passed.
	 */
	public FilterResult filterObject(T object);

}
