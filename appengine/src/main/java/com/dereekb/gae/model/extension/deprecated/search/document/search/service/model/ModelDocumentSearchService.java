package com.dereekb.gae.model.extension.search.document.search.service.model;

import com.dereekb.gae.model.extension.deprecated.search.document.search.service.DocumentSearchRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for searching a particular model.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <Q>
 *            Request Type
 */
public interface ModelDocumentSearchService<T extends UniqueModel, R> {

	// TODO: Update ModelDocumentSearchService to not be parameterized by type

	/**
	 * Performs a model search.
	 *
	 * @param request
	 *            Request model. Never {@code null}.
	 * @return {@link ModelDocumentSearchResponse}. Never {@code null}.
	 */
	public ModelDocumentSearchResponse<T> search(R request);

	/**
	 * Searches for models that match.
	 *
	 * @param request
	 *            {@link DocumentSearchRequest}. Never {@code null}.
	 * @return {@link ModelDocumentSearchResponse}. Never {@code null}.
	 */
	public ModelDocumentSearchResponse<T> search(DocumentSearchRequest request);

}
