package com.dereekb.gae.model.crud.services.response.pair;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * Used by {@link UpdateResponse} to communicate which templates failed, and for
 * what reason.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InvalidTemplatePair<T extends UniqueModel> extends HandlerPair<T, InvalidAttribute>
        implements KeyedInvalidAttribute, AlwaysKeyed<UniqueModel> {

	public InvalidTemplatePair(T template, InvalidAttribute failure) {
		super(template, failure);
	}

	public T getTemplate() {
		return this.key;
	}

	public InvalidAttribute getFailure() {
		return this.object;
	}

	// MARK: KeyedInvalidAttribute
	@Override
	public String getAttribute() {
		return this.object.getAttribute();
	}

	@Override
	public String getValue() {
		return this.object.getValue();
	}

	@Override
	public String getDetail() {
		return this.object.getDetail();
	}

	@Override
	public ErrorInfo getError() {
		return this.object.getError();
	}

	// MARK: Keyed
	@Override
	public UniqueModel keyValue() {
		return this.getKey();
	}

	// MARK: Utility
	public static <T extends UniqueModel> List<InvalidTemplatePair<T>> makeWithUpdatePairs(Iterable<UpdatePair<T>> pairs) {
		List<InvalidTemplatePair<T>> failurePairs = new ArrayList<InvalidTemplatePair<T>>();

		for (UpdatePair<T> pair : pairs) {
			if (pair.hasFailed()) {
				T template = pair.getTemplate();
				InvalidAttribute e = pair.getFailureException();

				InvalidTemplatePair<T> failurePair = new InvalidTemplatePair<T>(template, e);
				failurePairs.add(failurePair);
			}
		}

		return failurePairs;
	}

}
