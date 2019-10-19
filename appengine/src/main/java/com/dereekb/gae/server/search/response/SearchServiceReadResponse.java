package com.dereekb.gae.server.search.response;

import java.util.List;

import com.google.appengine.api.search.Document;

/**
 * Search service read response.
 *
 * @author dereekb
 *
 */
public interface SearchServiceReadResponse {

	/**
	 * Returns the first {@link Document}.
	 *
	 * @return first {@link Document} in the response from
	 *         {@link #getDocuments()}, or {@code null} if no document results.
	 */
	public Document getFirstDocument();

	/**
	 * Returns a {@link List} of result {@link Document}.
	 *
	 * @return {@link List} of found documents. Never {@code null}.
	 */
	public List<Document> getDocuments();

	/**
	 * Returns the identifiers of missing documents.
	 *
	 * @return {@link List} of identifiers of missing documents. Never
	 *         {@code null}.
	 */
	public List<String> getMissingDocuments();

}
