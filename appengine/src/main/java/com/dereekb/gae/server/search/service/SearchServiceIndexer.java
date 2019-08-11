package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.exception.SearchIndexUpdateException;
import com.dereekb.gae.server.search.request.SearchServiceDeleteRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequest;

/**
 * Services for updating/deleting search documents.
 *
 * @author dereekb
 *
 */
public interface SearchServiceIndexer {

	/**
	 * Updates the index.
	 *
	 * @param request
	 *            {@link SearchServiceUpdateRequest}. Never {@code null}.
	 * @throws SearchIndexUpdateException
	 */
	public void updateIndex(SearchServiceIndexRequest request) throws SearchIndexUpdateException;

	/**
	 * Deletes items from the index.
	 *
	 * @param request
	 *            {@link SearchServiceDeleteRequest}. Never {@code null}.
	 */
	public void deleteFromIndex(SearchServiceDeleteRequest request);

}
