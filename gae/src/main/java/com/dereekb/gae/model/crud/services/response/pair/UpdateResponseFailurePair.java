package com.dereekb.gae.model.crud.services.response.pair;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedAttributeUpdateFailure;

/**
 * Used by {@link UpdateResponse} to communicate which templates failed, and for
 * what reason.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class UpdateResponseFailurePair<T extends UniqueModel> extends HandlerPair<T, AttributeUpdateFailure>
        implements KeyedAttributeUpdateFailure, AlwaysKeyed<UniqueModel> {

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

	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDetail() {
		// TODO Auto-generated method stub
		return null;
	}

}
