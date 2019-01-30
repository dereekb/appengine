package com.dereekb.gae.utilities.filters.impl;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link Filter} that uses a {@link Filter} to delegate methods to.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class AbstractDelegatedFilter<T>
        implements Filter<T> {

	private Filter<T> filter;

	protected AbstractDelegatedFilter() {}

	public AbstractDelegatedFilter(Filter<T> filter) {
		this.setFilter(filter);
	}

	public Filter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<T> filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}

		this.filter = filter;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(T object) {
		return this.filter.filterObject(object);
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
		return this.filter.filterObjects(objects);
	}

}
