package com.dereekb.gae.model.extension.search.document.search.service.model;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.service.response.SearchDocumentQueryResponse;

/**
 * Contains {@link ModelKey} search results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelDocumentSearchResponse<T>
        extends SearchDocumentQueryResponse {

	/**
	 * Returns the {@link ModelKey} search results.
	 *
	 * @return {@link Collection} of results. Never {@code null}.
	 */
	public Collection<ModelKey> getKeySearchResults();

	/**
	 * Returns the model search results.
	 *
	 * @return {@link Collection} of results. Never {@code null}.
	 */
	public Collection<T> getModelSearchResults();

}
