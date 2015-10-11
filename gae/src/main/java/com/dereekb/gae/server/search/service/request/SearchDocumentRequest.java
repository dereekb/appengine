package com.dereekb.gae.server.search.service.request;


/**
 * Base request for the Search Document API.
 * <p>
 * Requests target a specific index.
 *
 * @author dereekb
 *
 */
public interface SearchDocumentRequest {

	/**
	 * Search document index.
	 *
	 * @return the index name. Never {@code null}.
	 */
	public String getIndexName();

}
