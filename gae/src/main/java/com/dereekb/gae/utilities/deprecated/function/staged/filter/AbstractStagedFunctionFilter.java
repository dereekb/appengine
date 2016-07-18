package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResults;

public abstract class AbstractStagedFunctionFilter<T> extends AbstractFilter<T>
        implements StagedFunctionFilter<T> {

	/**
	 * By default responds the same way to all stages.
	 */
	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate) {
		return this.filterObjectsWithDelegate(sources, delegate);
	}

}
