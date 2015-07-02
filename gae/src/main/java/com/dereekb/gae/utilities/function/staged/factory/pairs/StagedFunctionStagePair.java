package com.dereekb.gae.utilities.function.staged.factory.pairs;

import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

public class StagedFunctionStagePair<T> extends HandlerPair<StagedFunctionStage[], T> {

	public StagedFunctionStagePair(StagedFunctionStage[] stages, T observer) {
		super(stages, observer);
	}

	public static <T> StagedFunctionStagePair<T> fromFactoryWithStage(StagedFunctionStage stage,
	                                                                   T observer) {
		StagedFunctionStage[] stages = { stage };
		return new StagedFunctionStagePair<T>(stages, observer);
	}

	public StagedFunctionStage[] getStages() {
		return this.key;
	}

	public T getObject() {
		return object;
	}

}
