package com.thevisitcompany.gae.deprecated.authentication.login.functions;

import com.thevisitcompany.gae.deprecated.authentication.login.filters.LoginDependentFilter;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilterDelegate;

public abstract class AbstractLoginDependentFunctionHandlerFilter<T> extends LoginDependentFilter<T>
        implements LoginDependentFunctionHandlerFilter<T> {

	@Override
    public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
                                                          Iterable<W> sources,
                                                          StagedFunctionFilterDelegate<T, W> delegate) {
	    return super.filterObjectsWithDelegate(sources, delegate);
    }

}
