package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.AbstractStagedFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObjectObserver;

/**
 * Abstract class that implements the {@link StagedFunctionObjectObserverFactory} interface and contains a single
 * {@link StagedFunctionStage} that is used when creating the {@link StagedFunctionStagePair} for this observer.
 * 
 * @author dereekb
 *
 * @param <O> Type of the {@link StagedFunctionObjectObserver}.
 * @param <T> Type that the StagedFunction works with.
 * @param <W> Type of the {@link StagedFunctionObject} that the StagedFunction works with.
 * 
 * @see {@link AbstractStagedFunctionObjectObserverFactory} for the similar implementation that creates
 *      {@link StagedFunctionObjectObserver} instead.
 * @see {@link com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction} for more information about
 *      StagedFunctions.
 */
public abstract class AbstractStagedFunctionObjectObserverFactory<O extends StagedFunctionObjectObserver<T, W>, T, W extends StagedFunctionObject<T>> extends AbstractStagedFactory<O>
        implements StagedFunctionObjectObserverFactory<O, T, W> {

	public AbstractStagedFunctionObjectObserverFactory(StagedFunctionStage functionStage) {
		super(functionStage);
	}

	@Override
	public O make() {
		O observer = this.generateObserver();
		return observer;
	}

	@Override
	public StagedFunctionStagePair<O> makeObjectObserver() {
		O observer = this.generateObserver();
		StagedFunctionStage[] stages = this.getStages(observer);
		StagedFunctionStagePair<O> pair = new StagedFunctionStagePair<O>(stages, observer);
		return pair;
	}

	public abstract O generateObserver();

}
