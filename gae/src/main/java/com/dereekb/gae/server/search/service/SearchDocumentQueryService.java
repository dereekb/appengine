package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.service.request.DocumentQueryRequest;
import com.dereekb.gae.server.search.service.response.SearchDocumentQueryResponse;


public interface SearchDocumentQueryService {

	/**
	 * Queries for documents.
	 *
	 * @param request
	 *            {@link DocumentQueryRequest}. Never {@code null}.
	 * @return {@link SearchDocumentQueryResponse}. Never {@code null}.
	 */
	public SearchDocumentQueryResponse queryDocuments(DocumentQueryRequest request);

}
