package com.dereekb.gae.model.extension.search.document.search.components;

import com.dereekb.gae.server.search.document.query.deprecated.builder.DocumentQueryBuilder;

/**
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
public interface DocumentSearchQueryConverter<Q> {

	/**
	 * Generates a {@link DocumentQueryBuilderExpression} instance from the input query.
	 */
	public DocumentQueryBuilder convertQuery(Q query);

}
