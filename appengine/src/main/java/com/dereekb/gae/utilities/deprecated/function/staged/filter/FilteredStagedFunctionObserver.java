package com.dereekb.gae.utilities.function.staged.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;

/**
 * Observer that is called when objects are filtered out in a {@link FilteredStagedFunction}.
 *
 * This allows modifications to the filtered out objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            Object type
 * @param <W>
 *            StagedFunctionObject with the object type <T>
 */
public interface FilteredStagedFunctionObserver<T, W extends StagedFunctionObject<T>> {

	public void objectsWereFilteredOut(Iterable<W> objects,
	                                   StagedFunctionStage stage);

}
