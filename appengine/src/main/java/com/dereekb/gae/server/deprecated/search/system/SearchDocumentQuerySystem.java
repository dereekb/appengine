package com.dereekb.gae.server.search.system;

import com.dereekb.gae.server.deprecated.search.system.request.DocumentQueryRequest;
import com.dereekb.gae.server.deprecated.search.system.response.SearchDocumentQueryResponse;


public interface SearchDocumentQuerySystem {

	/**
	 * Queries for documents.
	 *
	 * @param request
	 *            {@link DocumentQueryRequest}. Never {@code null}.
	 * @return {@link SearchDocumentQueryResponse}. Never {@code null}.
	 */
	public SearchDocumentQueryResponse queryDocuments(DocumentQueryRequest request);

}
