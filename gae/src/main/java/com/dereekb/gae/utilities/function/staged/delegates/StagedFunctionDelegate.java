package com.dereekb.gae.utilities.function.staged.delegates;

import java.util.Collection;

import com.dereekb.gae.utilities.function.staged.StagedFunction;

/**
 * Delegate of a function handler.
 * @author dereekb
 */
public interface StagedFunctionDelegate<T> {
	
	/**
	 * Whether or not filtering out a single object in a pre-filter will result in the function to fail.
	 * @return True if the handler should fail on filtering out a single object.
	 */
	public boolean handlerRequiresAllInitialObjects(StagedFunction<T, ?> handler);
	
	/**
	 * Returns true if the handler should clear all objects.
	 * @return
	 */
	public boolean handlerShouldClearOnCompletion(StagedFunction<T, ?> functionsHandler);
		
	/**
	 * Returns true if the handler should save it's changes.
	 * @param wrappers
	 * @return
	 */
	public boolean handlerShouldSave(Collection<T> objects, StagedFunction<T, ?> handler);
	
}
