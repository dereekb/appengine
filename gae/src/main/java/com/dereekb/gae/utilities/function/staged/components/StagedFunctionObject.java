package com.dereekb.gae.utilities.function.staged.components;

import com.dereekb.gae.utilities.function.staged.StagedFunction;

/**
 * Represents an object used by the {@link StagedFunction} that wraps the model type.
 * 
 * @author dereekb
 * @param <T> Model type used by the StagedFunction.
 */
public interface StagedFunctionObject<T> {

	/**
	 * Returns the object used by the {@link StagedFunction} at the given stage.
	 * 
	 * An example usage is during creation where the source object may be returned until a save is complete, at which point the newly saved
	 * model is returned instead.
	 * 
	 * @param stage
	 *            The stage at which the function handler is at.
	 * @return Model the implementation decides is most important for a given stage.
	 */
	public T getFunctionObject(StagedFunctionStage stage);

}
