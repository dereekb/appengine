package com.dereekb.gae.model.crud.function.filters.misc;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionObjectFilter;

/**
 * Basic read filter that filters out {@link ReadPair} instances that have no
 * results.
 *
 * @author dereekb
 */
public class HasResultFilter<T extends UniqueModel> extends AbstractStagedFunctionObjectFilter<T, ReadPair<T>> {

	@Override
	public FilterResult filterObject(ReadPair<T> object) {
		Object result = object.getResult();
		boolean hasResult = (result != null);
		return FilterResult.withBoolean(hasResult);
	}

}
