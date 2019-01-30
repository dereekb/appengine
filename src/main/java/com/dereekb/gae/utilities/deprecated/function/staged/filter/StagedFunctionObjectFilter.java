package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObjectDependent;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Function handler filter that is passed the StagedFunctionObjects instead of the items themselves.
 * 
 * @author dereekb
 */
public interface StagedFunctionObjectFilter<T, W extends StagedFunctionObject<T>>
        extends StagedFunctionObjectDependent {

	public FilterResults<W> filterFunctionObjects(StagedFunctionStage stage,
	                                              Iterable<W> objects);

}
