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

	/**
	 * Filters an input collection of an alternate type that contains the object
	 * to filter.
	 *
	 * @param sources
	 *            Sources to retrieve the object from.
	 * @param delegate
	 *            Delegate to read the filtered types from.
	 * @return {@link FilterResults} with the input source objects.
	 * 
	 * @deprecated This pattern belongs in its own type.
	 */
	@Deprecated
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
	                                                      FilterDelegate<T, W> delegate);

}
