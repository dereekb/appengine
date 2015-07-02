package com.dereekb.gae.model.extension.search.document.index.observer;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.function.observers.ConditionalSaveObjectsObserverDelegate;
import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Implements the {@link ConditionalSaveObjectsObserverDelegate} to be used in
 * conjunction with an {@link DocumentIndexFunction} to filter out elements that
 * are being updated (not indexed or unindexed) so they are not needlessly
 * saved.
 *
 * @author dereekb
 */
public class SaveIndexChangesDelegate<T extends UniqueSearchModel>
        implements ConditionalSaveObjectsObserverDelegate<T, IndexPair<T>> {

	@Override
	public Iterable<T> filterObjectsToSave(Iterable<IndexPair<T>> pairs) {
		List<T> filteredObjects = new ArrayList<T>();

		for (IndexPair<T> pair : pairs) {
			if (pair.modelWasChange()) {
				T model = pair.getModel();
				filteredObjects.add(model);
			}
		}

		return filteredObjects;
	}

}
