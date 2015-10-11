package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.service.exception.DocumentPutException;
import com.dereekb.gae.server.search.service.request.DocumentPutRequest;

/**
 * Used for indexing and updating search documents.
 *
 * @author dereekb
 *
 */
public interface SearchDocumentIndexService {

	/**
	 * Performs a put on the search service.
	 *
	 * @param request
	 *            {@link DocumentPutRequest}. Never {@code null}.
	 * @throws DocumentPutException
	 *             if an exception occurs.
	 */
	public void put(DocumentPutRequest request) throws DocumentPutException;

}
