package com.dereekb.gae.model.crud.function.observers;

import com.dereekb.gae.model.crud.function.pairs.ReadPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunctionObserver;

/**
 * Function that clears the result from a {@link ReadPair} if the pair was filtered out.
 *
 * @author dereekb
 */
public class ReadFunctionFilterObserver<T extends UniqueModel>
        implements FilteredStagedFunctionObserver<T, ReadPair<T>> {

	@Override
	public void objectsWereFilteredOut(Iterable<ReadPair<T>> pairs,
                                       StagedFunctionStage stage) {
		for (ReadPair<T> pair : pairs) {
			pair.clearResult();
		}
    }

}
