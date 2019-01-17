package com.dereekb.gae.server.datastore.models.query;

import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;

/**
 * Interface for returning results from a model query.
 *
 * @author dereekb
 *
 */
public interface IndexedModelQueryResponse {

	/**
	 * Checks whether or not any results exist.
	 *
	 * @return {@code true} if one or more results are available.
	 */
	public boolean hasResults();

	/**
	 * Returns the number of results available in the response.
	 *
	 * @return {@link Integer}. Never {@code null} nor less than 0.
	 */
	public Integer getResultCount();

	/**
	 * Returns the cursor string of the last item for this query request.
	 *
	 * @return {@link String}. Never {@code null}.
	 * @throws NoSearchCursorException
	 *             if no cursor is available.
	 */
	public String getCursorString() throws NoSearchCursorException;

}
