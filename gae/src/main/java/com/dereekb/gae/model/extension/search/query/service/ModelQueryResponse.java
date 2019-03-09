package com.dereekb.gae.model.extension.search.query.service;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.model.search.response.ModelSearchResponse;
import com.googlecode.objectify.Key;

/**
 * Contains {@link ModelKey} query results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryResponse<T>
        extends ModelSearchResponse<T> {

	/**
	 * Returns a list of objectify keys corresponding to the results in
	 * {@link #getKeyResults()};
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<Key<T>> getObjectifyKeyResults();

}
