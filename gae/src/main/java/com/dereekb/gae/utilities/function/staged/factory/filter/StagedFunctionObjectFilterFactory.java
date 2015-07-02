package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionObjectFilter;

public interface StagedFunctionObjectFilterFactory<F extends StagedFunctionObjectFilter<T, W>, T, W extends StagedFunctionObject<T>>
        extends Factory<F> {

	public StagedFunctionStagePair<F> makeObjectFilter();

}
