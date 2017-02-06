package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResults;

public abstract class AbstractStagedFunctionObjectFilter<T, W extends StagedFunctionObject<T>> extends AbstractFilter<W>
        implements StagedFunctionObjectFilter<T, W> {

	@Override
	public FilterResults<W> filterFunctionObjects(StagedFunctionStage stage,
	                                              Iterable<W> objects) {
		return this.filterObjects(objects);
	}

}
