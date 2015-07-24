package com.dereekb.gae.model.extension.visibility.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.visibility.VisibilityState;
import com.dereekb.gae.model.extension.visibility.VisibilityStateModifier;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Function used for changing the state of multiple models using an
 * {@link VisibilityStateModifier} and {@link VisibilityPair} instances.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class VisibilityFunction<T> extends FilteredStagedFunction<T, VisibilityPair<T>> {

	private final VisibilityStateModifier<T> modifier;

	public VisibilityFunction(VisibilityStateModifier<T> modifier) {
		this.modifier = modifier;
	}

	@Override
	protected void doFunction() {
		Collection<VisibilityPair<T>> pairs = this.getWorkingObjects();
		HashMapWithList<VisibilityState, VisibilityPair<T>> map = VisibilityPair.getChangesMap(pairs);

		for (VisibilityState state : map.getKeySet()) {
			List<VisibilityPair<T>> pairsForState = map.getElements(state);
			this.makeChanges(pairsForState, state);
		}
	}

	private void makeChanges(List<VisibilityPair<T>> pairs,
	                         VisibilityState state) {
		if (pairs.size() > 0) {
			try {
				List<T> models = VisibilityPair.getKeys(pairs);
				this.modifier.setState(models, state);
				VisibilityPair.setResultPairsSuccess(pairs, true);
			} catch (AtomicOperationException e) {
				VisibilityPair.setResultPairsSuccess(pairs, false);
			}
		}
	}

}
