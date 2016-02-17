package com.dereekb.gae.server.search.system;

import com.dereekb.gae.server.search.system.exception.DocumentPutException;
import com.dereekb.gae.server.search.system.request.DocumentPutRequest;

/**
 * Used for indexing and updating search documents.
 *
 * @author dereekb
 *
 */
public interface SearchDocumentIndexSystem {

	/**
	 * Performs a put on the search indexService.
	 *
	 * @param request
	 *            {@link DocumentPutRequest}. Never {@code null}.
	 * @throws DocumentPutException
	 *             if an exception occurs.
	 */
	public void put(DocumentPutRequest request) throws DocumentPutException;

}
