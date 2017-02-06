package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Pre-configured Objectify query response accessor.
 * 
 * @author dereekb
 * 
 * @see ObjectifyQueryKeyResponse
 */
public interface ObjectifyQueryResponse {

	/**
	 * Returns the created query.
	 *
	 * @return {@link SimpleQuery} used. Never {@code null}.
	 */
	public SimpleQuery<?> getQuery();

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
	 * Returns the cursor of the last item for this query request.
	 * 
	 * @return {@link Cursor}. Never {@code null}.
	 * @throws NoSearchCursorException
	 *             if no cursor is available.
	 */
	public Cursor getCursor() throws NoSearchCursorException;

}
