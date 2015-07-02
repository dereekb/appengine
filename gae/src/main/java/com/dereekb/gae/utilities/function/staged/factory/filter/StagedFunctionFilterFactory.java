package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFilter;

public interface StagedFunctionFilterFactory<F extends StagedFunctionFilter<T>, T>
        extends Factory<F> {

	public StagedFunctionStagePair<F> makeFilter();

}
