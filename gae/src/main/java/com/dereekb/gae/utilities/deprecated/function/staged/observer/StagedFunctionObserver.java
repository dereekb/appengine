package com.dereekb.gae.utilities.function.staged.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;

/**
 * Observes a {@link StagedFunction}'s tasks. Has accessing the objects of type T being used by StagedFunction.
 * 
 * Used by {@link com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObserverMap}.
 * 
 * @param <T>
 *            Type of the object used by the StagedFunction.
 * @author dereekb
 * @see {@link StagedFunctionObjectObserver} to see object observers which have access to the {@link StagedFunctionObject}.
 * @see {@link com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction} for more information about
 *      StagedFunctions.
 */
public interface StagedFunctionObserver<T> {

	/**
	 * Called when the function handler reaches the specified stage.
	 * 
	 * @param stage Stage the function handler is at.
	 * @param handler Handler that called this stage.
	 */
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler);

}
