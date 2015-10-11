package com.dereekb.gae.server.search.service.request;

import java.util.Collection;

/**
 * Used for indexing items using a {@link SearchDocumentIndexService}.
 *
 * @author dereekb
 *
 */
public interface DocumentPutRequest
        extends SearchDocumentRequest {

	/**
	 * Returns all {@link DocumentPutRequestModel} for this request.
	 *
	 * @return {@link Collection} of request models. Never {@code null}.
	 */
	public Collection<DocumentPutRequestModel> getPutRequestModels();

}
