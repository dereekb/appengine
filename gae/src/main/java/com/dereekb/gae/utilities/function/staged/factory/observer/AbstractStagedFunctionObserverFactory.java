package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.AbstractStagedFactory;
import com.dereekb.gae.utilities.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObjectObserver;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Abstract class that implements the {@link StagedFunctionObserverFactory} interface and contains a single
 * {@link StagedFunctionStage} that is used when creating the {@link StagedFunctionStagePair}.
 * 
 * @author dereekb
 *
 * @param <O>
 *            Type of the {@link StagedFunctionObserver}.
 * @param <T>
 *            Type that the StagedFunction works with.
 * @see {@link AbstractStagedFunctionObjectObserverFactory} for the similar implementation that creates
 *      {@link StagedFunctionObjectObserver} instead.
 * @see {@link com.dereekb.gae.utilities.function.staged.StagedFunction} for more information about
 *      StagedFunctions.
 */
public abstract class AbstractStagedFunctionObserverFactory<O extends StagedFunctionObserver<T>, T> extends AbstractStagedFactory<O>
        implements StagedFunctionObserverFactory<O, T> {

	public AbstractStagedFunctionObserverFactory(StagedFunctionStage functionStage) {
		super(functionStage);
	}

	@Override
	public O make() {
		O observer = this.generateObserver();
		return observer;
	}

	@Override
	public StagedFunctionStagePair<O> makeObserverStagePair() {
		O observer = this.make();
		StagedFunctionStage[] stages = this.getStages(observer);
		StagedFunctionStagePair<O> pair = new StagedFunctionStagePair<O>(stages, observer);
		return pair;
	}

	public abstract O generateObserver();

}
