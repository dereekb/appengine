package com.dereekb.gae.model.crud.function.filters;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionFilter;

public class CanCreateFilter<T> extends AbstractStagedFunctionFilter<T> {

	private CanCreateFilterDelegate<T> delegate;

	public CanCreateFilter() {}

	public CanCreateFilter(CanCreateFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public FilterResult filterObject(T object) {
		return FilterResult.withBoolean(this.delegate.canCreate(object));
	}

	public CanCreateFilterDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CanCreateFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
