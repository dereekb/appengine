package com.dereekb.gae.model.crud.pairs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;

/**
 * Basic delete pair.
 * Result is boolean of whether or not the object was deleted.
 *
 * @author dereekb
 * @param <T>
 *            Model type being deleted.
 */
public class DeletePair<T extends UniqueModel> extends SuccessResultsPair<T> {

	public DeletePair(T object) {
		super(object);
		this.object = true;
	}

	public static <T extends UniqueModel> List<DeletePair<T>> deletePairsForModels(Collection<T> models) {
		List<DeletePair<T>> pairs = new ArrayList<>();

		for (T model : models) {
			DeletePair<T> pair = new DeletePair<>(model);
			pairs.add(pair);
		}

		return pairs;
	}

}
