package com.dereekb.gae.utilities.filters.impl;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterDelegate;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link Filter} that uses a {@link AbstractFilter} to delegate methods to.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DelegatedAbstractFilter<T>
        implements Filter<T> {

	private AbstractFilter<T> filter;

	protected DelegatedAbstractFilter() {}

	public DelegatedAbstractFilter(AbstractFilter<T> filter) {
		this.setFilter(filter);
	}

	public AbstractFilter<T> getFilter() {
		return this.filter;
	}

	public void setFilter(AbstractFilter<T> filter) {
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

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
	                                                      FilterDelegate<T, W> delegate) {
		return this.filter.filterObjectsWithDelegate(sources, delegate);
	}

}
