package com.dereekb.gae.model.crud.services.response.pair;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.util.AttributeUpdateFailure;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Used by {@link UpdateResponse} to communicate which templates failed, and for
 * what reason.
 *
 * @author dereekb
 *
 * @param <T>
 *            Template type
 */
public final class UpdateResponseFailurePair<T> extends HandlerPair<T, AttributeUpdateFailure> {

	public UpdateResponseFailurePair(T template, AttributeUpdateFailure failure) {
		super(template, failure);
	}

	public T getTemplate() {
		return this.key;
	}

	public AttributeUpdateFailure getFailure() {
		return this.object;
	}

	public static <T extends UniqueModel> List<UpdateResponseFailurePair<T>> createFailurePairs(Iterable<UpdatePair<T>> pairs) {
		List<UpdateResponseFailurePair<T>> failurePairs = new ArrayList<UpdateResponseFailurePair<T>>();

		for (UpdatePair<T> pair : pairs) {
			if (pair.hasFailed()) {
				T template = pair.getTemplate();
				AttributeUpdateFailure e = pair.getFailureException();

				UpdateResponseFailurePair<T> failurePair = new UpdateResponseFailurePair<T>(template, e);
				failurePairs.add(failurePair);
			}
		}

		return failurePairs;
	}

}
