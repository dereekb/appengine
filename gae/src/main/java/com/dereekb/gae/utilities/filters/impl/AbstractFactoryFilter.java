package com.dereekb.gae.utilities.filters.impl;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterDelegate;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Anstract {@link Filter} that creates a filter each time the input is
 * requested.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractFactoryFilter<T>
        implements Filter<T> {

	@Override
	public FilterResult filterObject(T object) {
		return this.makeFilter().filterObject(object);
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
		return this.makeFilter().filterObjects(objects);
	}

	@Deprecated
	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
	                                                      FilterDelegate<T, W> delegate) {
		return this.makeFilter().filterObjectsWithDelegate(sources, delegate);
	}

	// MARK: Internal
	protected abstract Filter<T> makeFilter();

}
