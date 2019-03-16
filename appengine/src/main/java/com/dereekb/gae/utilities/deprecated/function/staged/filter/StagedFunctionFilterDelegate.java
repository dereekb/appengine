package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.FilterDelegate;

/**
 * Similar to FilterDelegate, except has the stage passed along too.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Model Type
 * @param <U>
 *            Function Object Type
 */
public interface StagedFunctionFilterDelegate<T, U>
        extends FilterDelegate<T, U> {

	/**
	 * For FunctionFilterDelegate, the default stage used is at the delegate's discretion.
	 * 
	 * For cases where this is called, the filter generally does not care what stage the function is at.
	 */
	public T getModel(U source);

	public T getModel(U source,
	                  StagedFunctionStage stage);

}
