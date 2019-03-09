package com.dereekb.gae.utilities.function.staged.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObjectDependent;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;

/**
 * Observes a function handler's tasks.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Model type
 * @param <W>
 *            StagedFunctionObject type with Model type <T>
 */
public interface StagedFunctionObjectObserver<T, W extends StagedFunctionObject<T>>
        extends StagedFunctionObjectDependent {

	/**
	 * Called when the function handler reaches the specified stage.
	 * 
	 * @param stage
	 *            Stage the function handler is at.
	 * @param handler
	 *            Handler that called this stage.
	 */
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, W> handler);

}
