package com.dereekb.gae.utilities.function.staged.factory.filter;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;

/**
 * Factory that creates new {@link StagedFunctionFiltersMap} objects.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Type of model used by the StagedFunction.
 * @param <W>
 *            {@link StagedFunctionObject} type that wraps the model.
 */
public interface StagedFunctionFilterMapFactory<T, W extends StagedFunctionObject<T>> {

	/**
	 * Creates a new filters map from this Factory.
	 */
	public StagedFunctionFiltersMap<T, W> makefiltersMap();

}
