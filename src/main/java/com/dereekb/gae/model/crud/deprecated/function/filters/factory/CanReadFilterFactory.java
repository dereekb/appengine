package com.dereekb.gae.model.crud.deprecated.function.filters.factory;

import com.dereekb.gae.model.crud.deprecated.function.filters.CanReadFilter;
import com.dereekb.gae.model.crud.deprecated.function.filters.CanReadFilterDelegate;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.filter.AbstractStagedFunctionFilterFactory;

public class CanReadFilterFactory<T> extends AbstractStagedFunctionFilterFactory<CanReadFilter<T>, T> {

	private CanReadFilterDelegate<T> filterDelegate;

	public CanReadFilterFactory() {
		super(StagedFunctionStage.FINISHED);
	}

	@Override
	public CanReadFilter<T> generateFilter() {
		CanReadFilter<T> filter = new CanReadFilter<T>();

		if (filterDelegate != null) {
			filter.setDelegate(filterDelegate);
		}

		return filter;
	}

	public CanReadFilterDelegate<T> getFilterDelegate() {
		return filterDelegate;
	}

	public void setFilterDelegate(CanReadFilterDelegate<T> filterDelegate) {
		this.filterDelegate = filterDelegate;
	}

}
