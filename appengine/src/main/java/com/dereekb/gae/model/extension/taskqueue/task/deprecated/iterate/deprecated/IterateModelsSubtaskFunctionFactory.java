package com.dereekb.gae.model.extension.taskqueue.task.iterate.deprecated;

import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

@Deprecated
public class IterateModelsSubtaskFunctionFactory<T> extends AbstractFilteredStagedFunctionFactory<IterateModelsSubtaskFunction<T>, T, SuccessResultsPair<T>> {

	@Override
	protected IterateModelsSubtaskFunction<T> newStagedFunction() {
		IterateModelsSubtaskFunction<T> function = new IterateModelsSubtaskFunction<T>();
		return function;
	}

}
