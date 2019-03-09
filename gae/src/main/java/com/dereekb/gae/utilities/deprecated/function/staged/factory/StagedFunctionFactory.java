package com.dereekb.gae.utilities.function.staged.factory;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;

/**
 * Factory dedicated to making new Function Handlers of type <F>
 * 
 * @author dereekb
 * 
 * @param <F>
 *            Function Handler Type
 * @param <T>
 *            Model Type
 * @param <W>
 *            Function Model Type
 */
@Deprecated
public interface StagedFunctionFactory<F extends StagedFunction<T, W>, T, W extends StagedFunctionObject<T>> {

	/**
	 * Creates a new function handler.
	 * 
	 * @return
	 */
	public F makeStagedFunction();

}
