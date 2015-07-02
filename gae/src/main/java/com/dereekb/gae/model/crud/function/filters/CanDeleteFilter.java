package com.dereekb.gae.model.crud.function.filters;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionFilter;

public class CanDeleteFilter<T> extends AbstractStagedFunctionFilter<T> {

	private CanDeleteFilterDelegate<T> delegate;

	public CanDeleteFilter() {}

	public CanDeleteFilter(CanDeleteFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResult filterObject(T object) {
		return FilterResult.withBoolean(delegate.canDelete(object));
	}

	public CanDeleteFilterDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(CanDeleteFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
