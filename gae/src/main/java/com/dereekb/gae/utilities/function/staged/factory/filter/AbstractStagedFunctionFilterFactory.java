package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.AbstractStagedFactory;
import com.dereekb.gae.utilities.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFilter;

public abstract class AbstractStagedFunctionFilterFactory<F extends StagedFunctionFilter<T>, T> extends AbstractStagedFactory<F>
        implements StagedFunctionFilterFactory<F, T> {

	public AbstractStagedFunctionFilterFactory(StagedFunctionStage functionStage) {
		super(functionStage);
	}

	@Override
	public F make() {
		F filter = this.generateFilter();
		return filter;
	}

	@Override
	public StagedFunctionStagePair<F> makeFilter() {
		F filter = this.make();
		StagedFunctionStage[] stages = this.getStages(filter);
		StagedFunctionStagePair<F> pair = new StagedFunctionStagePair<F>(stages, filter);
		return pair;
	}

	public abstract F generateFilter();

}
