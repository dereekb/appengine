package com.dereekb.gae.model.crud.pairs;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Defines a pair to process a creation with, and return the results.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreatePair<T extends UniqueModel> extends ResultsPair<T, T> {

	private InvalidAttributeException failureException;

	public CreatePair(T source) {
		super(source);
	}

	public InvalidAttributeException getFailureException() {
		return this.failureException;
	}

	public void setFailureException(InvalidAttributeException failureException) {
		this.setResult(null);
		this.failureException = failureException;
	}

	// MARK: Utility
	public static <T extends UniqueModel> List<CreatePair<T>> createPairsForModels(Iterable<T> models) {
		List<CreatePair<T>> pairs = new ArrayList<>();

		for (T model : models) {
			CreatePair<T> pair = new CreatePair<>(model);
			pairs.add(pair);
		}

		return pairs;
	}

}