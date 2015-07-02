package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Factory that returns a singleton that implements the {@link StagedFunctionObserver} interface that has already been created.
 * 
 * @author dereekb
 *
 * @param <O>
 * @param <T>
 */
public class SingletonStagedFunctionObserverFactory<O extends StagedFunctionObserver<T>, T> extends AbstractStagedFunctionObserverFactory<O, T> {

	private final O singleton;

	public SingletonStagedFunctionObserverFactory(O singleton, StagedFunctionStage functionStage) {
		super(functionStage);
		this.singleton = singleton;
	}

	@Override
	public O generateObserver() {
		return this.singleton;
	}

}
