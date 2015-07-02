package com.thevisitcompany.gae.deprecated.authentication.login.functions;

import com.thevisitcompany.gae.deprecated.authentication.login.filters.LoginDependentFilter;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionObject;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;

public abstract class AbstractLoginDependentFunctionObjectHandlerFilter<T, W extends StagedFunctionObject<T>> extends LoginDependentFilter<W>
        implements LoginDependentFunctionObjectHandlerFilter<T, W> {

	@Override
	public FilterResults<W> filterFunctionObjects(StagedFunctionStage stage,
	                                              Iterable<W> objects) {
		return this.filterFunctionObjects(stage, objects);
	}

}
