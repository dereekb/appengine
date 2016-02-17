package com.dereekb.gae.model.crud.deprecated.function.filters;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionFilter;

public class CanReadFilter<T> extends AbstractStagedFunctionFilter<T> {

	private CanReadFilterDelegate<T> delegate;

	public CanReadFilter() {}

	public CanReadFilter(CanReadFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResult filterObject(T object) {
		return FilterResult.withBoolean(delegate.canRead(object));
	}

	public CanReadFilterDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(CanReadFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
