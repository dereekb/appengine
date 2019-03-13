package com.dereekb.gae.utilities.function.staged.delegates;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;

/**
 * Delegate used by a {@link StagedFunction} for saving changes.
 *
 * @author dereekb
 */
public interface StagedFunctionSaveDelegate<T> {

	/**
	 * Handles saving the models.
	 * 
	 * @param models
	 * @return True if all models were saved successfully.
	 */
	public boolean saveFunctionChanges(Iterable<T> models);

}
