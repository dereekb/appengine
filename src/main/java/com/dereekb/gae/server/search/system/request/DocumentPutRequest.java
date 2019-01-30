package com.dereekb.gae.server.search.system.request;

import java.util.Collection;

import com.dereekb.gae.server.search.system.SearchDocumentIndexSystem;

/**
 * Used for indexing items using a {@link SearchDocumentIndexSystem}.
 *
 * @author dereekb
 *
 */
public interface DocumentPutRequest
        extends SearchDocumentRequest {

	/**
	 * Whether or not we are update all the input models.
	 * <p>
	 * Updated models are not notified of the identifier their document is saved
	 * with.
	 *
	 * @return {@link true} if updating.
	 */
	public boolean isUpdate();

	/**
	 * Returns all {@link DocumentPutRequestModel} for this request.
	 *
	 * @return {@link Collection} of request models. Never {@code null}.
	 */
	public Collection<DocumentPutRequestModel> getPutRequestModels();

}
