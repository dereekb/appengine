package com.dereekb.gae.server.search.request;

/**
 * Request that references documents by their keys.
 *
 * @author dereekb
 *
 */
public interface SearchServiceKeysRequest
        extends SearchServiceRequest {

	/**
	 * Returns the keys corresponding to the documents to read.
	 *
	 * @return {@link Iterable}. Never {@code null}.
	 */
	public Iterable<String> getDocumentKeys();

}
