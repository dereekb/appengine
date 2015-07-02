package com.dereekb.gae.utilities.function;

import com.dereekb.gae.utilities.function.staged.delegates.StagedFunctionSaveDelegate;

/**
 * A {@link Function} that has a side-effect that may require saving.
 * 
 * Saves are processed through a {@link StagedFunctionSaveDelegate}.
 * 
 * @author dereekb
 *
 * @param <W>
 */
public interface SavableFunction<T, W>
        extends Function<W> {

	/**
	 * Whether or not the function's effects were saved successfully.
	 * 
	 * @return
	 */
	public boolean savedSuccessfully();

	/**
	 * 
	 * @param saveDelegate
	 */
	public void setSaveDelegate(StagedFunctionSaveDelegate<T> saveDelegate);

}
