package com.dereekb.gae.utilities.function.staged.delegates;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;

/**
 * Delegate that doesn't actually save, but causes the {@link StagedFunction} to notify observers and filters of the save-related
 * stages.
 *
 * @author dereekb
 * @param <T>
 */
public class FakeStagedFunctionSaveDelegate<T>
        implements StagedFunctionSaveDelegate<T> {

	@Override
	public boolean saveFunctionChanges(Iterable<T> models) {
		return true;
	}

}
