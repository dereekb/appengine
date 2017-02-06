package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.FallableObjectFilter;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.FallableStagedFunctionObject;

public class FallableObjectFilterFactory<T, W extends FallableStagedFunctionObject<T>> extends AbstractStagedFunctionObjectFilterFactory<FallableObjectFilter<T, W>, T, W> {

	public FallableObjectFilterFactory() {
		super(StagedFunctionStage.FUNCTION_STARTED);
	}

	@Override
	public FallableObjectFilter<T, W> generateFilter() {
		FallableObjectFilter<T, W> filter = new FallableObjectFilter<T, W>();
		return filter;
	}

}
