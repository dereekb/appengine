package com.dereekb.gae.model.extension.search.document.search.components;

import com.dereekb.gae.server.search.document.DocumentQueryBuilder;

/**
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
public interface DocumentSearchQueryConverter<Q> {

	/**
	 * Generates a {@link DocumentQueryBuilder} instance from the input query.
	 */
	public DocumentQueryBuilder convertQuery(Q query);

}
