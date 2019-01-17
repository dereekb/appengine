package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryResponse;
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
public interface ObjectifyQueryResponse extends IndexedModelQueryResponse {

	/**
	 * Returns the created query.
	 *
	 * @return {@link SimpleQuery} used. Never {@code null}.
	 */
	public SimpleQuery<?> getQuery();

	/**
	 * Returns the cursor of the last item for this query request.
	 *
	 * @return {@link Cursor}. Never {@code null}.
	 * @throws NoSearchCursorException
	 *             if no cursor is available.
	 */
	public Cursor getCursor() throws NoSearchCursorException;

}
