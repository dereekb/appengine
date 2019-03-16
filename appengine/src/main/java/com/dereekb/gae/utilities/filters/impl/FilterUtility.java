package com.dereekb.gae.utilities.filters.impl;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterDelegate;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

public class FilterUtility {

	/**
	 * Filters a collection of objects.
	 *
	 * @param objects
	 *            Objects to filter.
	 * @return {@link FilterResults} mapping of the results.
	 */
	public static <T> FilterResults<T> filterObjects(Filter<T> filter,
	                                                 Iterable<? extends T> objects) {
		FilterResults<T> results = new FilterResults<T>();

		for (T object : objects) {
			FilterResult result = filter.filterObject(object);
			results.add(result, object);
		}

		return results;
	}

	public static class FilterInstance<T> {

		private final Filter<T> filter;

		public FilterInstance(Filter<T> filter) {
			this.filter = filter;
		}

		public Filter<T> getFilter() {
			return this.filter;
		}

		// MARK: Filter Functions
		public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
			return FilterUtility.filterObjects(this.filter, objects);
		}

		@Deprecated
		public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
		                                                      FilterDelegate<T, W> delegate) {
			FilterResults<W> results = new FilterResults<W>();

			for (W source : sources) {
				T object = delegate.getModel(source);
				FilterResult result = this.filter.filterObject(object);
				results.add(result, source);
			}

			return results;
		}

	}

}
