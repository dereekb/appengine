package com.dereekb.gae.model.extension.search.document.search.service;

import com.dereekb.gae.server.search.system.response.SearchDocumentQueryResponse;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Search service.
 *
 * @author dereekb
 */
public interface DocumentSearchService {

	/**
	 * Searches for documents that match.
	 *
	 * @param request
	 *            {@link DocumentSearchRequest}. Never {@code null}.
	 * @return {@link SearchDocumentQueryResponse} of {@link ScoredDocument}
	 *         elements.
	 */
	public SearchDocumentQueryResponse search(DocumentSearchRequest request);

}
