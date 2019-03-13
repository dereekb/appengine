package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.AbstractStagedFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionObjectFilter;

public abstract class AbstractStagedFunctionObjectFilterFactory<F extends StagedFunctionObjectFilter<T, W>, T, W extends StagedFunctionObject<T>> extends AbstractStagedFactory<F>
        implements StagedFunctionObjectFilterFactory<F, T, W> {

	public AbstractStagedFunctionObjectFilterFactory(StagedFunctionStage functionStage) {
		super(functionStage);
	}

	@Override
	public F make() {
		F filter = this.generateFilter();
		return filter;
	}

	@Override
	public StagedFunctionStagePair<F> makeObjectFilter() {
		F filter = this.make();
		StagedFunctionStage[] stages = this.getStages(filter);
		StagedFunctionStagePair<F> pair = new StagedFunctionStagePair<F>(stages, filter);
		return pair;
	}

	public abstract F generateFilter();

}
