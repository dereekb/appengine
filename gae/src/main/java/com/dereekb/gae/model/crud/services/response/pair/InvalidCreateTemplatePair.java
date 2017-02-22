package com.dereekb.gae.model.crud.services.response.pair;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;

/**
 * Used by {@link CreateResponse} to communicate which templates failed, and for
 * what reason.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InvalidCreateTemplatePair<T extends UniqueModel> extends InvalidTemplatePair<T> {

	private final ModelKey index;

	public InvalidCreateTemplatePair(int index, T template, InvalidAttribute failure) {
		super(template, failure);
		this.index = new ModelKey(index);
	}

	public ModelKey getIndex() {
		return this.index;
	}

	// MARK: Keyed
	@Override
	public UniqueModel keyValue() {
		return this.index;
	}

	// MARK: Utility
	public static <T extends UniqueModel> List<InvalidCreateTemplatePair<T>> makeWithCreatePairs(Iterable<CreatePair<T>> pairs) {
		List<InvalidCreateTemplatePair<T>> failurePairs = new ArrayList<InvalidCreateTemplatePair<T>>();

		int i = 0;
		for (CreatePair<T> pair : pairs) {
			InvalidAttribute e = pair.getAttributeFailure();

			if (e != null) {
				T template = pair.getKey();
				InvalidCreateTemplatePair<T> failurePair = new InvalidCreateTemplatePair<T>(i, template, e);
				failurePairs.add(failurePair);
			}

			i += 1;
		}

		return failurePairs;
	}

}
