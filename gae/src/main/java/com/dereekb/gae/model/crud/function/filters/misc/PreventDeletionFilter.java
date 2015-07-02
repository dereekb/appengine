package com.dereekb.gae.model.crud.function.filters.misc;

import com.dereekb.gae.model.crud.function.pairs.DeletePair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FallableObjectFilter;

/**
 * Used by the Cruds DeleteFunction to filter out pairs that should not be deleted.
 *
 * @author dereekb
 *
 * @param <T>
 *            Object type being deleted.
 */
@Deprecated
public class PreventDeletionFilter<T> extends FallableObjectFilter<T, DeletePair<T>> {

	@Override
	public FilterResults<DeletePair<T>> filterFunctionObjects(StagedFunctionStage stage,
	                                                          Iterable<DeletePair<T>> objects) {

		FilterResults<DeletePair<T>> results = new FilterResults<DeletePair<T>>();

		for (DeletePair<T> pair : objects) {
			boolean shouldKeep = pair.isSuccessful();
			FilterResult result = FilterResult.withBoolean(shouldKeep);
			results.add(result, pair);
		}

		return results;
	}

}
