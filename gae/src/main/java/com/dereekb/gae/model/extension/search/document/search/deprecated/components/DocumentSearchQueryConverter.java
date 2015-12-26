package com.dereekb.gae.model.extension.search.document.search.deprecated.components;

import com.dereekb.gae.server.search.document.query.deprecated.builder.DocumentQueryBuilder;

/**
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
@Deprecated
public interface DocumentSearchQueryConverter<Q> {

	/**
	 * Generates a {@link DocumentQueryBuilderExpression} instance from the input query.
	 */
	public DocumentQueryBuilder convertQuery(Q query);

}
