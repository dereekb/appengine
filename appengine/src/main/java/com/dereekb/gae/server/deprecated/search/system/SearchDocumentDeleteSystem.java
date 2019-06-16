package com.dereekb.gae.server.search.system;

import com.dereekb.gae.server.deprecated.search.system.request.DocumentIdentifierRequest;

public interface SearchDocumentDeleteSystem {

	/**
	 * Deletes all documents corresponding to the identifiers in the request.
	 *
	 * @param request
	 *            {@link DocumentIdentifierRequest}. Never {@code null}.
	 */
	public void deleteDocuments(DocumentIdentifierRequest request);

}
