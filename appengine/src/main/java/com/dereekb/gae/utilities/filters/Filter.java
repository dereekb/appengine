package com.dereekb.gae.utilities.filters;

/**
 * Used for filtering values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Filter<T>
        extends SingleObjectFilter<T> {

	/**
	 * Filters a collection of objects.
	 *
	 * @param objects
	 *            Objects to filter.
	 * @return {@link FilterResults} mapping of the results.
	 */
	public FilterResults<T> filterObjects(Iterable<? extends T> objects);

}
