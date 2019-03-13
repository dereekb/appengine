package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Filters models by accessing the basic models being used by a {@link com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction}.
 * 
 * Used by {@link StagedFunctionFiltersMap}. Is called when the {@link StagedFunctionStage} this filter is listening for is reached by the
 * StagedFunction.
 * 
 * @param <T>
 *            Type of the object used by the StagedFunction.
 * @author dereekb
 * @see {@link StagedFunctionObjectFilter} to use filters which have access to the {@link StagedFunctionObject}.
 * @see {@link com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction} for more information about
 *      StagedFunctions.
 */
public interface StagedFunctionFilter<T> {

	/**
	 * Filters the objects using a {@link StagedFunctionFilterDelegate}.
	 * 
	 * @param stage Stage that the StagedFunction is currently at.
	 * @param sources StagedFunction's current objects.
	 * @param delegate Delegate for retrieving the correct objects from the delegate.
	 * @return A {@link FilterResults} object containing the set of items that passed and failed.
	 */
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate);

}
