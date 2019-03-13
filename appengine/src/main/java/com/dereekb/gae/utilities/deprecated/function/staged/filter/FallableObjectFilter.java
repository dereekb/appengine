package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Used by the Cruds DeleteFunction to filter out pairs that should not be deleted.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Object type being deleted.
 */
public class FallableObjectFilter<T, W extends FallableStagedFunctionObject<T>>
        implements StagedFunctionObjectFilter<T, W> {

	@Override
	public FilterResults<W> filterFunctionObjects(StagedFunctionStage stage,
	                                              Iterable<W> objects) {

		FilterResults<W> results = new FilterResults<W>();

		for (W pair : objects) {
			boolean hasNotFailed = (!pair.hasFailed());
			FilterResult result = FilterResult.withBoolean(hasNotFailed);
			results.add(result, pair);
		}

		return results;
	}

}
