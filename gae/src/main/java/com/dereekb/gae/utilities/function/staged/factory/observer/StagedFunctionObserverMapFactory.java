package com.dereekb.gae.utilities.function.staged.factory.observer;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserverMap;

public interface StagedFunctionObserverMapFactory<T, W extends StagedFunctionObject<T>> {

	/**
	 * Creates a new observer map.
	 */
	public StagedFunctionObserverMap<T, W> makeObserverMap();

}
