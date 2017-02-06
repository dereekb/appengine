package com.dereekb.gae.model.crud.deprecated.function.filters;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionFilter;

public class CanUpdateFilter<T> extends AbstractStagedFunctionFilter<T> {

	private CanUpdateFilterDelegate<T> delegate;

	public CanUpdateFilter() {}

	public CanUpdateFilter(CanUpdateFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResult filterObject(T object) {
		return FilterResult.withBoolean(delegate.canUpdate(object));
	}

	public CanUpdateFilterDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(CanUpdateFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
