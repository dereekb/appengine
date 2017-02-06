package com.dereekb.gae.model.crud.pairs;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;

/**
 * Defines a pair to process a creation with, and return the results.
 * @author dereekb
 *
 * @param <T>
 */
public class CreatePair<T extends UniqueModel> extends ResultsPair<T, T> {

	/**
	 * Source object to use during creation.
	 *
	 * @param source
	 */
	public CreatePair(T source) {
		super(source);
	}

	public static <T extends UniqueModel> List<CreatePair<T>> createPairsForModels(Iterable<T> models) {
		List<CreatePair<T>> pairs = new ArrayList<>();

		for (T model : models) {
			CreatePair<T> pair = new CreatePair<>(model);
			pairs.add(pair);
		}

		return pairs;
	}

}