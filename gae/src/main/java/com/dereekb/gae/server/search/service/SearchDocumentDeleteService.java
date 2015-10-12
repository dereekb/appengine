package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.service.request.DocumentIdentifierRequest;

public interface SearchDocumentDeleteService {

	/**
	 * Deletes all documents corresponding to the identifiers in the request.
	 *
	 * @param request
	 *            {@link DocumentIdentifierRequest}. Never {@code null}.
	 */
	public void deleteDocuments(DocumentIdentifierRequest request);

}
