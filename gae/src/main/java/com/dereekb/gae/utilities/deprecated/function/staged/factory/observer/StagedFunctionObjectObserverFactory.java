package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObjectObserver;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Factory dedicated to making Object Observers
 * 
 * @author dereekb
 * 
 * @param <O>
 *            Observer type
 * @param <T>
 *            Model Type
 * @param <W>
 *            Function Model Type
 */
public interface StagedFunctionObjectObserverFactory<O extends StagedFunctionObjectObserver<T, W>, T, W extends StagedFunctionObject<T>>
        extends Factory<O> {

	/**
	 * Makes a new Function Handler Object Observer of type <T>.
	 */
	public StagedFunctionStagePair<O> makeObjectObserver();

}
