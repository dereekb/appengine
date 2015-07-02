package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Factory dedicated to making Function Handler Observers
 * 
 * @author dereekb
 * 
 * @param <O>
 *            Observer type
 * @param <T>
 *            Model type
 */
public interface StagedFunctionObserverFactory<O extends StagedFunctionObserver<T>, T>
        extends Factory<O> {

	/**
	 * Makes a new Function Handler Observer, wrapped in a pair that shows the stage also.
	 */
	public StagedFunctionStagePair<O> makeObserverStagePair();

}
