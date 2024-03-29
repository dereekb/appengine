package com.dereekb.gae.server.search.system.response;

import java.util.Collection;

import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Search document query response.
 * 
 * @author dereekb
 *
 */
public interface SearchDocumentQueryResponse {

	/**
	 * @return {@link Collection} of {@link ScoredDocument} values. Never
	 *         {@code null}.
	 */
	public Collection<ScoredDocument> getDocumentResults();

	/**
	 * Returns the total number of results that match the query.
	 *
	 * @return {@link Long} for the total number of results. Never {@code null}.
	 */
	public Long getFoundResults();

	/**
	 * Returns the number of returned results.
	 *
	 * @return {@link Integer} for the number of returned results. Never
	 *         {@code null}.
	 */
	public Integer getReturnedResults();

	/**
	 * Returns the cursor for the results for resuming the query later.
	 * 
	 * @return {@link Cursor}.
	 * 
	 * @throws NoSearchCursorException
	 *             if no cursor is available.
	 */
	public Cursor getResultsCursor() throws NoSearchCursorException;

}
